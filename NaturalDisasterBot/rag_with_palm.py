from llama_index import SimpleDirectoryReader, VectorStoreIndex
from llama_index.embeddings import HuggingFaceEmbedding
from llama_index.llms.palm import PaLM
from llama_index import ServiceContext
from llama_index import StorageContext
import os
import storage.data

class RAGPaLMQuery:
    import storage.data
    def __init__(self):


        # Create a folder for data if it doesn't exist
        # if not os.path.exists("storage/data"):
        #     print("I did this")
        #     os.makedirs("data")

        # Load documents from the data folder


        #self.documents = SimpleDirectoryReader("./storage/data").load_data()
        self.input_dir = SimpleDirectoryReader("D:\\NaturalDisasterBot\storage\data").load_data()


        print(self.input_dir)

        # Set up API key for PaLM
        os.environ['GOOGLE_API_KEY'] = 'AIzaSyCm4E0i4O2KKbyoRUqbZc0-AS2plr26S5A'

        # Initialize PaLM and Hugging Face embedding model
        self.llm = PaLM()
        self.embed_model = HuggingFaceEmbedding(model_name="BAAI/bge-small-en")
        print("ok")

        if not self.input_dir:
            # Provide a default document or handle the situation
            default_document = "This is a default document."
            self.documents = [default_document]
            print("didnt find")


        # Set up service context
        self.service_context = ServiceContext.from_defaults(llm=self.llm, embed_model=self.embed_model, chunk_size=800, chunk_overlap=20)

        # Create a VectorStoreIndex from the documents
        self.index = VectorStoreIndex.from_documents(self.input_dir, service_context=self.service_context)

        # Persist the index to storage
        self.index.storage_context.persist()

        # Create a query engine
        self.query_engine = self.index.as_chat_engine()

    def query_response(self, query):
        # Perform a query
        response = self.query_engine.chat(query)
        return response

