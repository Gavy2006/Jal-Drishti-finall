from app.rag.vector_store import search_similar


def retrieve_context(
    query: str,
    top_k: int = 5,
) -> list[dict]:

    results = search_similar(
        query=query,
        top_k=top_k,
    )

    documents = results.get("documents", [[]])[0]
    metadatas = results.get("metadatas", [[]])[0]

    context = []

    for document, metadata in zip(
        documents,
        metadatas,
    ):
        context.append(
            {
                "text": document,
                "source": metadata.get("source"),
                "page": metadata.get("page"),
            }
        )

    return context