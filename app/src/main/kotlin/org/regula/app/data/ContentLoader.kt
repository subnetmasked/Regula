package org.regula.app.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import org.regula.app.data.entity.Category
import org.regula.app.data.entity.Citation
import org.regula.app.data.entity.Entry

object ContentLoader {
    private const val ASSET_PATH = "content/content.json"

    private val VALID_CLASSIFICATIONS = setOf(
        "dogma",
        "doctrine",
        "discipline",
        "theological_opinion",
    )

    private val VALID_SOURCE_TYPES = setOf(
        "ccc",
        "canon_law",
        "aquinas",
        "magisterial",
        "scripture",
    )

    fun load(context: Context): ContentData {
        val jsonText = context.assets.open(ASSET_PATH).bufferedReader().use { it.readText() }
        return parse(jsonText)
    }

    fun parse(jsonText: String): ContentData {
        val root = JSONObject(jsonText)
        val contentVersion = root.getInt("contentVersion")
        if (contentVersion < 1) {
            throw ContentLoadException("contentVersion must be >= 1")
        }

        val categories = parseCategories(root.getJSONArray("categories"))
        val categoryIds = categories.map { it.id }.toSet()
        if (categoryIds.size != categories.size) {
            throw ContentLoadException("Duplicate category id found")
        }

        val entries = mutableListOf<Entry>()
        val citations = mutableListOf<Citation>()
        val entryIds = mutableSetOf<String>()

        val entriesArray = root.getJSONArray("entries")
        for (index in 0 until entriesArray.length()) {
            val entryObject = entriesArray.getJSONObject(index)
            val entry = parseEntry(entryObject, categoryIds)
            if (!entryIds.add(entry.id)) {
                throw ContentLoadException("Duplicate entry id: ${entry.id}")
            }
            entries.add(entry)
            citations.addAll(parseCitations(entryObject.getJSONArray("citations"), entry.id))
        }

        return ContentData(
            contentVersion = contentVersion,
            categories = categories,
            entries = entries,
            citations = citations,
        )
    }

    private fun parseCategories(array: JSONArray): List<Category> {
        return buildList {
            for (index in 0 until array.length()) {
                val item = array.getJSONObject(index)
                add(
                    Category(
                        id = requireNonBlank(item.getString("id"), "category.id"),
                        name = requireNonBlank(item.getString("name"), "category.name"),
                        sortOrder = item.getInt("sortOrder"),
                    ),
                )
            }
        }
    }

    private fun parseEntry(entryObject: JSONObject, categoryIds: Set<String>): Entry {
        val categoryId = requireNonBlank(entryObject.getString("categoryId"), "entry.categoryId")
        if (categoryId !in categoryIds) {
            throw ContentLoadException("Entry references unknown categoryId: $categoryId")
        }

        val classification = requireNonBlank(entryObject.getString("classification"), "entry.classification")
        if (classification !in VALID_CLASSIFICATIONS) {
            throw ContentLoadException("Unknown classification: $classification")
        }

        return Entry(
            id = requireNonBlank(entryObject.getString("id"), "entry.id"),
            categoryId = categoryId,
            question = requireNonBlank(entryObject.getString("question"), "entry.question"),
            shortAnswer = requireNonBlank(entryObject.getString("shortAnswer"), "entry.shortAnswer"),
            classification = classification,
            classificationNote = requireNonBlank(
                entryObject.getString("classificationNote"),
                "entry.classificationNote",
            ),
        )
    }

    private fun parseCitations(array: JSONArray, entryId: String): List<Citation> {
        if (array.length() == 0) {
            throw ContentLoadException("Entry $entryId must have at least one citation")
        }

        return buildList {
            for (index in 0 until array.length()) {
                val item = array.getJSONObject(index)
                val sourceType = requireNonBlank(item.getString("sourceType"), "citation.sourceType")
                if (sourceType !in VALID_SOURCE_TYPES) {
                    throw ContentLoadException("Unknown sourceType: $sourceType")
                }
                add(
                    Citation(
                        entryId = entryId,
                        sourceType = sourceType,
                        reference = requireNonBlank(item.getString("reference"), "citation.reference"),
                        summary = requireNonBlank(item.getString("summary"), "citation.summary"),
                    ),
                )
            }
        }
    }

    private fun requireNonBlank(value: String, field: String): String {
        if (value.isBlank()) {
            throw ContentLoadException("$field must not be blank")
        }
        return value
    }
}
