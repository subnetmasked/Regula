# Regula Content Authoring

All topics, articles, and citations are defined in JSON. There is no in-app editor — edit the file in your repo, bump the version, and rebuild.

## File to edit

**Shipped in the app:** [`app/src/main/assets/content/content.json`](../app/src/main/assets/content/content.json)

**Schema reference:** [`content/schema.json`](schema.json)

## Terminology

| JSON | App UI | Description |
|---|---|---|
| `categories[]` | Topics (browse grid) | Grouping labels, e.g. "Bioethics & Life Issues" |
| `entries[]` | Articles / Q&A | One question with a short answer |
| `entries[].citations[]` | Citations | Source references with paraphrased summaries |

## Adding a new topic (category)

1. Add an object to `categories`:

```json
{ "id": "liturgy", "name": "Liturgy & Worship", "sortOrder": 9 }
```

2. Use a stable snake_case `id` — it never changes once published.
3. Optionally add an icon mapping in `app/src/main/kotlin/org/regula/app/ui/components/CategoryVisuals.kt`. Without one, a default gold icon is used.

## Adding a new article (entry)

1. Add an object to `entries` with a unique `id` and valid `categoryId`:

```json
{
  "id": "bioethics_euthanasia",
  "categoryId": "bioethics",
  "question": "Is euthanasia ever permitted?",
  "shortAnswer": "…",
  "classification": "doctrine",
  "classificationNote": "…",
  "citations": [
    {
      "sourceType": "ccc",
      "reference": "2276-2279",
      "summary": "Paraphrased summary in your own words — not verbatim magisterial text."
    }
  ]
}
```

2. Every entry needs at least one citation.

## Editing existing content

Change any text field directly in `content.json`. Do not rename `id` fields unless you intend to break bookmarks (there are none yet in v0.1).

## Deploying changes to a device

1. Edit `app/src/main/assets/content/content.json`
2. **Bump `contentVersion`** (required — the app only reloads when this number changes)
3. Rebuild and install:

```bash
export JAVA_HOME=/path/to/jdk-17
./gradlew installDebug
```

No uninstall or "clear data" needed.

## Allowed values

**`classification`:** `dogma`, `doctrine`, `discipline`, `theological_opinion`

**`sourceType`:** `ccc`, `canon_law`, `aquinas`, `magisterial`, `scripture`

**`reference`:** Use `"TBD"` if you are unsure of an exact citation number.

## Citation summaries

Write summaries in your own words based on standard Catholic teaching. Do not paste verbatim magisterial text. Do not invent CCC or canon numbers — use `"TBD"` until verified.
