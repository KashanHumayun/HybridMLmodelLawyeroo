import firebase_admin
from firebase_admin import credentials, db
import pandas as pd
import logging
import tensorflow as tf
import tensorflow_recommenders as tfrs
from sklearn.preprocessing import MultiLabelBinarizer
from tensorflow.keras.layers import StringLookup, Embedding, Concatenate, Dense
from tensorflow.keras import layers
import numpy as np
from flask import Flask, request, jsonify
import threading
from apscheduler.schedulers.background import BackgroundScheduler
import atexit

app = Flask(__name__)


# Initialize logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Firebase configuration
service_account_key = {
    "type": "service_account",
    "project_id": "lawyeroo",
    "private_key_id": "5146ce399e6a018f63c54cdf771902b5b86ae881",
    "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCKQ2Up7ItfUZHy\nH5wuve4s5/7IzQ8BjrGolbeGk+/3nrWCQbIft6khLUCADiMa1ATGgQ+4yWt4Y1ND\nkZ4sSzErrTMjhpmDbuR6s5xa4bgLN3mvYhAfcD+VYyBJKxbrL6GVFYBIo7o3Df+F\nb8W5kTZS5WL/jPBCVFQBFNmNvfslIfQt8MapJePv0/4Tlcq89CAzqL+imjzvpwfV\nc0N/oeJyax9a/0fhAdg2sbyNt3QMUY3f086g9GSZBspsFowUIiFKZXbGsf3c2Z6X\nRIPMGPVMF8WJeDGs1waYUvunUENuYUERzwlwNkblb/iEAryVWVUPTW6KrlO+1d+m\nrT5RGQ8BAgMBAAECggEAC+h/E4vMezKu08A2Nu5jz5lRn+DNbJBqK79tVZlzPwv7\nzRK0cTfjyLg5iG3vRxXArYobHQq4B1/ioXyut1jojL73mxUyLphD9G94xXkQXWLz\nR5A2HEcSKt8lPjgIWsw46fq2mEsFtE0v10HHsVJY7+3Sx3jPjp7tt0Qg8qzMrydr\nkz3g6zZceQBcP1jkecj+UIo/2eiPXmvpG8ZKLeWEyjbL8rKlwMLcUCdpAXhVXrCY\nQ+QjJxzGicxpa4RHJ7oLKgReE0QBzUNOH8rsu2hwnSNAgtoB5lZgf2f6c56bvb2m\nBAFW3w4I/TZjRk5ibFaYekChOSfXMtnEGyEGOgIA4QKBgQC/hyu0Bkz/pIbXTVl1\nVj+wTw9/arUtb6gzktfKeTj6sBpkWU0smgHPPsfMT9Xn/kiaJ9rKid+3b0TxmiU4\n6/84feUylBslsbCJLwfHsEIqmphJs8w3CIpkgMhtJiyokNToyLsFNJvMQRqeW947\nkxaO1H49AziI4JhFxbzZNqioaQKBgQC4zilpNmqDw78GFmRvOOBKLgBBJBRC6NF5\nUTdD89nflbS0rhTd6u01tZioM9eqzb9YPwSxTti8A14O+dKLX2nqc5VUPzGiWA5U\nd6H2qw8NYfndS6LraPD1MKsawcQBWW/24Yq2l36hOhMopO01B+3hVedHCV+ZLJ0e\nS0m52HYe2QKBgQCmzd11E0u6FZtOJdJ0i6I6NhDkQpX6NVWDXS9M6piCVB8YKGI7\nJwHOcfnmad19CAxJHc7COKO7iU+9Lyed1INZuuODUlP2VzPNtkULV+m4Wx2oisuf\nXnPRJgvoa6fXQxP6jN+8n93UXRsGsjHxJqAt0skC8CwroZdcpONdNepbiQKBgGg7\nv5ecHY+cBE4GqzcgOUEdmP8kvljOv/dRG360NxqocXfHB/Xy3z//4M5eDzJDQb0g\nLiFje1LwA0knFt2NP0wBTSRrUC9kwxBZOXzLFn5R2IA8fPOSCPYCMT+yMj8glsoE\nhBxgbge5nby66R+UDVOfBAdI/iaCx0ui4z5zHIxhAoGBAJgA5eIZAKHTwz6sTRGR\nE3ubCccbPrSH6gmFhsH2m0n+RWZSqCfxtq1UQngS0F//BSfbcUG27iC4koT4VFua\nPVY7kxZzi3zRMPJ/MfNXvnY4yWfKcGluZ1lj5PQ8eqOcOJENi65FFyG28XXmT4E4\nFdC1SQijfY4LUjPN3D6KzbFc\n-----END PRIVATE KEY-----\n",
    "client_email": "firebase-adminsdk-k1fyb@lawyeroo.iam.gserviceaccount.com",
    "client_id": "117502601114588062839",
    "auth_uri": "https://accounts.google.com/o/oauth2/auth",
    "token_uri": "https://oauth2.googleapis.com/token",
    "auth_provider_x509_cert_url": "https://www.googleapis.com/v1/certs",
    "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-k1fyb@lawyeroo.iam.gserviceaccount.com",
    "universe_domain": "googleapis.com"
}
# Initialize Firebase
cred = credentials.Certificate(service_account_key)
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://lawyeroo-default-rtdb.asia-southeast1.firebasedatabase.app'
})

lawyers_df = None
clients_df = None
interactions_df = None
ratings_df = None
views_df = None
client_interaction_matrix = None
combined_client_features = None
hyb_model= None
specializations_binarized = None
preferences_binarized = None
tf_client_features= None
tf_lawyer_features = None
combined_data = None
user_ids_mapping = None
lawyer_ids_mapping = None
dataset = None
model = None
# Fetch data from Firebase
def fetch_data():
    lawyers = db.reference('/lawyers').get()
    clients = db.reference('/clients').get()
    interactions = db.reference('/lawyer_interactions').get()
    ratings = db.reference('/lawyer_ratings').get()
    views = db.reference('/lawyer_profile_views').get()

    return lawyers, clients, interactions, ratings, views

def transform_data(lawyers, clients, interactions, ratings, views):
    # Transform dictionaries to DataFrames

    lawyers_df = pd.DataFrame([{'lawyer_id': k, **v} for k, v in lawyers.items()]) if lawyers else pd.DataFrame()
    clients_df = pd.DataFrame([{'client_id': k, **v} for k, v in clients.items()]) if clients else pd.DataFrame()

    # Filter out non-dict values from interactions
    valid_interactions = [v for v in interactions.values() if isinstance(v, dict)] if interactions else []
    interactions_df = pd.DataFrame(valid_interactions)

    ratings_df = pd.DataFrame(ratings.values()) if ratings else pd.DataFrame()
    views_df = pd.DataFrame(views.values()) if views else pd.DataFrame()

    return lawyers_df, clients_df, interactions_df, ratings_df, views_df

mlb = MultiLabelBinarizer()

def process_interactions(interactions_df, lawyers_df, clients_df):
    interaction_counts = interactions_df.groupby(['client_id', 'lawyer_id']).size().reset_index(name='counts')
    interaction_counts = interaction_counts.merge(lawyers_df[['lawyer_id', 'specializations']], on='lawyer_id')
    interaction_counts = interaction_counts.explode('specializations').groupby(['client_id', 'specializations']).sum(numeric_only=True).reset_index()

    client_interaction_matrix = interaction_counts.pivot(index='client_id', columns='specializations', values='counts').fillna(0)

    # Ensure all clients are included, even those without interactions
    client_interaction_matrix = client_interaction_matrix.reindex(clients_df['client_id']).fillna(0)
    client_interaction_matrix = client_interaction_matrix.reindex(columns=mlb.classes_, fill_value=0)

    return client_interaction_matrix

class LawyerRecommenderModel(tfrs.Model):
    def __init__(self, embedding_dimension, num_features):
        super().__init__()
        self.client_model = tf.keras.Sequential([
            layers.Dense(embedding_dimension, activation='relu'),
            layers.Dense(embedding_dimension, activation='relu')
        ])
        self.lawyer_model = tf.keras.Sequential([
            layers.Dense(embedding_dimension, activation='relu'),
            layers.Dense(embedding_dimension, activation='relu')
        ])
        self.task = tfrs.tasks.Retrieval()

    def compute_loss(self, features, training=False):
        client_embeddings = self.client_model(features[0])
        lawyer_embeddings = self.lawyer_model(features[1])
        return self.task(client_embeddings, lawyer_embeddings)
def recommend_lawyers_hybrid(client_id, top_n=7):
    global combined_client_features, lawyers_df, hyb_model, clients_df, specializations_binarized
    client_indices = clients_df.index[clients_df['client_id'] == client_id].tolist()
    if not client_indices:
        logger.error(f"No client found with ID {client_id}")
        return pd.DataFrame()  # Return an empty DataFrame if client is not found
    client_idx = client_indices[0]
    client_features = combined_client_features[client_idx:client_idx+1]

    if hasattr(hyb_model, 'client_model'):
        client_embedding = hyb_model.client_model.predict(client_features)
    else:
        raise AttributeError("Client model not found in the model.")

    lawyer_embeddings = hyb_model.lawyer_model.predict(specializations_binarized)
    similarities = tf.linalg.matmul(client_embedding, lawyer_embeddings, transpose_b=True)
    
    top_indices = tf.argsort(similarities, axis=-1, direction='DESCENDING')[0, :top_n].numpy()
    recommended_lawyers = lawyers_df.iloc[top_indices]
    
    return recommended_lawyers
def retrain_model():
    logger.info("Retraining model with new data...")
    global lawyers_df, clients_df, interactions_df, ratings_df, views_df
    global specializations_binarized, preferences_binarized, client_interaction_matrix, combined_client_features
    global tf_client_features, tf_lawyer_features, hyb_model
    # Fetch new data from Firebase
    lawyers, clients, interactions, ratings, views = fetch_data()
    lawyers_df, clients_df, interactions_df, ratings_df, views_df = transform_data(lawyers, clients, interactions, ratings, views)
    
    # Binarize new data
    specializations_binarized = mlb.fit_transform(lawyers_df['specializations'])
    preferences_binarized = mlb.transform(clients_df['preferences'])
    client_interaction_matrix = process_interactions(interactions_df, lawyers_df, clients_df)
    
    # Combine new feature vectors
    combined_client_features = np.hstack((preferences_binarized, client_interaction_matrix.values))
    
    tf_client_features = tf.data.Dataset.from_tensor_slices(combined_client_features).batch(10)
    tf_lawyer_features = tf.data.Dataset.from_tensor_slices(specializations_binarized).batch(10)
    
    # Retrain the model
    hyb_model = LawyerRecommenderModel(embedding_dimension=32, num_features=combined_client_features.shape[1])
    hyb_model.compile(optimizer=tf.keras.optimizers.Adagrad(0.1))
    hyb_model.fit(tf.data.Dataset.zip((tf_client_features, tf_lawyer_features)), epochs=10)

    logger.info("Model retrained and saved successfully.")



####Collaborative Filtering

def process_data():
    """Processes data and prepares it for the model."""
        
    global lawyers, clients, interactions, ratings, views
    lawyers, clients, interactions, ratings, views = fetch_data()
    global lawyers_df, clients_df, interactions_df, ratings_df, views_df 
    lawyers_df, clients_df, interactions_df, ratings_df, views_df = transform_data(lawyers, clients, interactions, ratings, views)
    global combined_data, user_ids_mapping, lawyer_ids_mapping, dataset
    print("check 111111111")
    
    combined_data = pd.concat([
        ratings_df[['client_id', 'lawyer_id']].assign(interaction=1),
        views_df[['client_id', 'lawyer_id']].assign(interaction=0.5)
    ])
    print("check 2222222222")
    print (combined_data)
    
    # Mapping string IDs to integers
    
    user_ids_mapping = {id: i for i, id in enumerate(combined_data['client_id'].unique())}
    
    lawyer_ids_mapping = {id: i for i, id in enumerate(combined_data['lawyer_id'].unique())}
    print ("233333333")
    print(user_ids_mapping)
    print(lawyer_ids_mapping)
    combined_data['client_id'] = combined_data['client_id'].map(user_ids_mapping)
    combined_data['lawyer_id'] = combined_data['lawyer_id'].map(lawyer_ids_mapping)
    
    print("check 3333333333pri3")
    print(combined_data['client_id'])
    print(combined_data['lawyer_id'])
    # Building the TensorFlow dataset
    dataset = tf.data.Dataset.from_tensor_slices({
        "client_id": combined_data['client_id'].values,
        "lawyer_id": combined_data['lawyer_id'].values,
        "interaction": combined_data['interaction'].values
    })
    print ("233333333")
    print (dataset)
# Define the model
class LawyerRecommender(tfrs.models.Model):

    print("22233333333333")
    def __init__(self, user_model, lawyer_model):
        super().__init__()
        self.lawyer_model: tf.keras.Model = lawyer_model
        self.user_model: tf.keras.Model = user_model
        self.task: tf.keras.layers.Layer = tfrs.tasks.Retrieval()
    print("2224444444")

    def compute_loss(self, features, training=False):
        user_embeddings = self.user_model(features["client_id"])
        lawyer_embeddings = self.lawyer_model(features["lawyer_id"])

        return self.task(user_embeddings, lawyer_embeddings, sample_weight=features["interaction"])



def define_model():
    """Defines and compiles the TensorFlow Recommender System Model."""
    global model
    embedding_dimension = 32

    print("check1")

    user_model = tf.keras.Sequential([
        tf.keras.layers.Embedding(len(user_ids_mapping) + 1, embedding_dimension)
    ])
    print("check2", user_model)
    lawyer_model = tf.keras.Sequential([
        tf.keras.layers.Embedding(len(lawyer_ids_mapping) + 1, embedding_dimension)
    ])
    print("check3", lawyer_model)
    model = LawyerRecommender(user_model, lawyer_model)
    model.compile(optimizer=tf.keras.optimizers.Adagrad(learning_rate=0.1))

def train_model():
    """Trains the recommender model."""
    print( "check 4")
    global model, dataset
    model.fit(dataset.batch(256), epochs=5)
    print( "check 5")

def retrain_collaborative_model():
    """Retrains the model by fetching new data and re-processing it."""
    print( "check 6")

    fetch_data()
    print( "check 7")
    
    process_data()
    print( "check 8")

    define_model()
    print( "check 9")

    train_model()
    print( "check 10")

    logger.info("Model retrained and updated successfully.")

def recommend_lawyers_using_collaborative_filtering(client_id, k=7):
    """Recommends lawyers using collaborative filtering."""
    global model, user_ids_mapping, lawyer_ids_mapping, lawyers_df
    print("check 1077")
    
    client_id_mapped = user_ids_mapping.get(client_id)
    if client_id_mapped is None:
        print("User IDs Mapping:", user_ids_mapping)
        raise ValueError(f"Client ID {client_id} not found in the dataset")
    print("check 1078, Client ID Mapped:", client_id_mapped)
    
    client_id_tensor = tf.constant([client_id_mapped], dtype=tf.int64)
    client_embedding = model.user_model(client_id_tensor)
    lawyer_embeddings = model.lawyer_model.layers[0].embeddings.numpy()
    print("check 1079, Client Embedding Shape:", client_embedding.shape)
    
    scores = tf.tensordot(client_embedding, lawyer_embeddings, axes=[[1], [1]])
    scores = tf.squeeze(scores)
    k = min(k, len(scores))
    top_k_indexes = tf.math.top_k(scores, k=k).indices.numpy()
    print("check 1080, Top K Indexes:", top_k_indexes)
    print("lawyer_ids_mapping:", lawyer_ids_mapping)
    print("Max index in lawyer_ids_mapping:", max(lawyer_ids_mapping.keys(), default="No keys found"))
    # Current mapping: {lawyer_id: index}
    # Invert it to: {index: lawyer_id}
    reversed_lawyer_ids_mapping = {value: key for key, value in lawyer_ids_mapping.items()}

    recommended_lawyer_ids = [reversed_lawyer_ids_mapping.get(idx, None) for idx in top_k_indexes]
    unmatched_indices = [idx for idx in top_k_indexes if idx not in lawyer_ids_mapping]
    if unmatched_indices:
        print("Unmatched indices:", unmatched_indices)
        
    recommended_lawyer_ids = [id for id in recommended_lawyer_ids if id is not None]
    print("Recommended Lawyers IDs (Filtered):", recommended_lawyer_ids)
    
    if not recommended_lawyer_ids:
        print("No valid lawyer IDs found for the given top indices.")
        return pd.DataFrame()  # Return empty DataFrame if no valid IDs found
    
    recommended_lawyers = lawyers_df[lawyers_df['lawyer_id'].isin(recommended_lawyer_ids)]
    print("check 1082, Recommended Lawyers Count:", len(recommended_lawyers))
    
    return recommended_lawyers

####Collaborative Filtering Ending
ref = db.reference('/')
################################################################

def client_listener(event):
    logger.info(f"Client listener triggered with event: {event}")
    retrain_model()

clients_watch = threading.Thread(target=ref.child('clients').listen, args=(client_listener,), daemon=True)
clients_watch.start()
# Example of using the function
retrain_model()

retrain_collaborative_model()

def combined_recommendation(client_id, k=7):
    # Check if client ID has rating or view data
    has_ratings = client_id in ratings_df['client_id'].values
    has_views = client_id in views_df['client_id'].values

    # Get hybrid recommendations
    hyb_recommended_lawyers = recommend_lawyers_hybrid(client_id, top_n=k)

    # Initialize combined recommendations
    combined_recommendations = hyb_recommended_lawyers

    if has_ratings or has_views:
        # Get collaborative filtering recommendations
        print('Client has ratings and views')
        try:
            cf_recommended_lawyers = recommend_lawyers_using_collaborative_filtering(client_id)
            # Combine both sets of recommendations
            combined_recommendations = pd.concat([hyb_recommended_lawyers, cf_recommended_lawyers])
            # Drop duplicates based on 'lawyer_id'
            combined_recommendations = combined_recommendations.drop_duplicates(subset=['lawyer_id'])
        except ValueError as e:
            logger.info(f"Client ID not found in collaborative filtering dataset: {e}")
    
    return combined_recommendations
#############Scheduler
scheduler = BackgroundScheduler()
scheduler.add_job(func=retrain_collaborative_model, trigger="interval", hours=3)
scheduler.start()

# Shut down the scheduler when exiting the app
atexit.register(lambda: scheduler.shutdown())

# Flask endpoint to manually trigger retraining (optional, for testing)
@app.route("/recommendations", methods=["GET"])
def get_recommendations():
    client_id = request.args.get("client_id")
    if not client_id:
        return jsonify({"error": "Client ID is required"}), 400

    try:
        recommendations = combined_recommendation(client_id)
        
        # Set 'years_of_experience' to 0 if NaN
        recommendations['years_of_experience'] = recommendations['years_of_experience'].fillna(0)
        
        # Prepare to filter out NaN values properly
        result = recommendations.apply(lambda x: x.dropna().to_dict(), axis=1).tolist()
        
        return jsonify({"recommended_lawyers": result}), 200
    except Exception as e:
        logger.error(f"Error during recommendation for client {client_id}: {e}")
        return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(debug=True, port=8000)