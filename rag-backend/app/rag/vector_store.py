from pathlib import Path
import os
import time
import chromadb
from dotenv import load_dotenv
from google import genai


load_dotenv()

VECTOR_DB_DIR = Path("data/chroma_db")
COLLECTION_NAME = "jal_drishti_documents"

EMBEDDING_MODEL = "gemini-embedding-001"

api_key = os.getenv("GEMINI_API_KEY")

if not api_key:
    raise RuntimeError(
        "GEMINI_API_KEY is not set. "
        "Check your .env file or deployment environment variables."
    )

client = genai.Client(
    api_key=api_key
)

chroma_client = chromadb.PersistentClient(
    path=str(VECTOR_DB_DIR)
)

collection = chroma_client.get_or_create_collection(
    name=COLLECTION_NAME
)

def generate_embeddings(
    texts: list[str],
) -> list[list[float]]:

    batch_size = 50
    all_embeddings = []

    for i in range(0, len(texts), batch_size):

        batch = texts[i:i + batch_size]

        while True:
            try:
                print(
                    f"Embedding batch "
                    f"{i + 1}-{i + len(batch)} / {len(texts)}"
                )

                response = client.models.embed_content(
                    model=EMBEDDING_MODEL,
                    contents=batch,
                )

                all_embeddings.extend(
                    embedding.values
                    for embedding in response.embeddings
                )

                break

            except Exception as e:

                if "429" not in str(e):
                    raise

                print(
                    "Gemini embedding quota reached. "
                    "Waiting 35 seconds..."
                )

                time.sleep(35)

    return all_embeddings



def add_chunks(chunks) -> int:

    if not chunks:
        return 0

    documents = [
        chunk.text
        for chunk in chunks
    ]

    ids = [
        f"{chunk.source}_{chunk.page}_{chunk.chunk_index}"
        for chunk in chunks
    ]

    metadatas = [
        {
            "source": chunk.source,
            "page": chunk.page,
            "chunk_index": chunk.chunk_index,
        }
        for chunk in chunks
    ]

    embeddings = generate_embeddings(
        documents
    )

    collection.upsert(
        ids=ids,
        documents=documents,
        metadatas=metadatas,
        embeddings=embeddings,
    )

    return len(chunks)


def search_similar(
    query: str,
    top_k: int = 5,
):
    query_embedding = generate_embeddings(
        [query]
    )[0]

    return collection.query(
        query_embeddings=[query_embedding],
        n_results=top_k,
    )