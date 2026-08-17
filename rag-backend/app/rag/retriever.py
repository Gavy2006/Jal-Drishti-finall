from app.rag.vector_store import search_similar


def retrieve_context(
    query: str,
    top_k: int = 5,
) -> list[dict]:

    results = search_similar(
        query=query,
        top_k=top_k,
    )

    documents = results.get(
        "documents",
        [[]],
    )[0]

    metadatas = results.get(
        "metadatas",
        [[]],
    )[0]

    context = []

    for document, metadata in zip(
        documents,
        metadatas,
    ):
        source = metadata.get(
            "source",
            "Unknown source",
        )

        page = metadata.get(
            "page",
            None,
        )

        context.append(
            {
                "text": document,
                "source": source,
                "page": page,
                "citation": (
                    f"{source} — Page {page}"
                    if page is not None
                    else source
                ),
            }
        )

    return context