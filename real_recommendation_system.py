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

lawyers, clients, interactions, ratings, views = fetch_data()
lawyers_df, clients_df, interactions_df, ratings_df, views_df = transform_data(lawyers, clients, interactions, ratings, views)

# Display the first few rows of each DataFrame
print("Lawyers Data:\n", lawyers_df.head())
print("\nClients Data:\n", clients_df.head())
print("\nInteractions Data:\n", interactions_df.head())
print("\nRatings Data:\n", ratings_df.head())
print("\nViews Data:\n", views_df.head())




# Binarize specializations and preferences
mlb = MultiLabelBinarizer()
specializations_binarized = mlb.fit_transform(lawyers_df['specializations'])
preferences_binarized = mlb.transform(clients_df['preferences'])

def process_interactions(interactions_df, lawyers_df, clients_df):
    interaction_counts = interactions_df.groupby(['client_id', 'lawyer_id']).size().reset_index(name='counts')
    interaction_counts = interaction_counts.merge(lawyers_df[['lawyer_id', 'specializations']], on='lawyer_id')
    interaction_counts = interaction_counts.explode('specializations').groupby(['client_id', 'specializations']).sum(numeric_only=True).reset_index()

    client_interaction_matrix = interaction_counts.pivot(index='client_id', columns='specializations', values='counts').fillna(0)

    # Ensure all clients are included, even those without interactions
    client_interaction_matrix = client_interaction_matrix.reindex(clients_df['client_id']).fillna(0)
    client_interaction_matrix = client_interaction_matrix.reindex(columns=mlb.classes_, fill_value=0)

    return client_interaction_matrix

client_interaction_matrix = process_interactions(interactions_df, lawyers_df, clients_df)

# Now combine preferences and interaction features
combined_client_features = np.hstack((preferences_binarized, client_interaction_matrix.values))

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

# Prepare the TensorFlow datasets
tf_client_features = tf.data.Dataset.from_tensor_slices(combined_client_features).batch(10)
tf_lawyer_features = tf.data.Dataset.from_tensor_slices(specializations_binarized).batch(10)

# Train the model
hyb_model = LawyerRecommenderModel(embedding_dimension=32, num_features=combined_client_features.shape[1])
hyb_model.compile(optimizer=tf.keras.optimizers.Adagrad(0.1))
hyb_model.fit(tf.data.Dataset.zip((tf_client_features, tf_lawyer_features)), epochs=10)

def recommend_lawyers_hybrid(client_id, top_n=5):
    client_idx = clients_df.index[clients_df['client_id'] == client_id].tolist()[0]
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


# Test the recommendation function with a client ID
test_client_id = clients_df['client_id'].iloc[0]  # Replace with an actual client ID
hyb_recommended_lawyers = recommend_lawyers_hybrid(test_client_id)
print("Recommended Lawyers:\n", hyb_recommended_lawyers)


# Preparing the combined data
combined_data = pd.concat([
    ratings_df[['client_id', 'lawyer_id']].assign(interaction=1),
    views_df[['client_id', 'lawyer_id']].assign(interaction=0.5)
])

# Mapping string IDs to integers
user_ids_mapping = {id: i for i, id in enumerate(combined_data['client_id'].unique())}
lawyer_ids_mapping = {id: i for i, id in enumerate(combined_data['lawyer_id'].unique())}

combined_data['client_id'] = combined_data['client_id'].map(user_ids_mapping)
combined_data['lawyer_id'] = combined_data['lawyer_id'].map(lawyer_ids_mapping)

# Building the TensorFlow dataset
dataset = tf.data.Dataset.from_tensor_slices({
    "client_id": combined_data['client_id'].values,
    "lawyer_id": combined_data['lawyer_id'].values,
    "interaction": combined_data['interaction'].values
})

# Define the model
class LawyerRecommender(tfrs.models.Model):

    def __init__(self, user_model, lawyer_model):
        super().__init__()
        self.lawyer_model: tf.keras.Model = lawyer_model
        self.user_model: tf.keras.Model = user_model
        self.task: tf.keras.layers.Layer = tfrs.tasks.Retrieval()

    def compute_loss(self, features, training=False):
        user_embeddings = self.user_model(features["client_id"])
        lawyer_embeddings = self.lawyer_model(features["lawyer_id"])

        return self.task(user_embeddings, lawyer_embeddings, sample_weight=features["interaction"])

# Define the user and lawyer models
embedding_dimension = 32

user_model = tf.keras.Sequential([
    tf.keras.layers.Embedding(len(user_ids_mapping) + 1, embedding_dimension)
])

lawyer_model = tf.keras.Sequential([
    tf.keras.layers.Embedding(len(lawyer_ids_mapping) + 1, embedding_dimension)
])

# Instantiate and compile the model
model = LawyerRecommender(user_model, lawyer_model)
model.compile(optimizer=tf.keras.optimizers.Adagrad(learning_rate=0.1))

# Train the model
model.fit(dataset.batch(256), epochs=5)
# Function to find top K recommended lawyers for a given client
def recommend_lawyers_using_collaborative_filtering(model, client_id, lawyer_ids_mapping, k=7):
    # Ensure the client_id is mapped
    client_id_mapped = user_ids_mapping.get(client_id)
    if client_id_mapped is None:
        raise ValueError("Client ID not found in the dataset")

    # Convert client_id to tensor
    client_id_tensor = tf.constant([client_id_mapped], dtype=tf.int64)

    # Compute embeddings for the given client
    client_embedding = model.user_model(client_id_tensor)

    # Correct way to get embeddings from the lawyer model
    lawyer_embeddings = model.lawyer_model.layers[0].embeddings.numpy()

    # Compute the scores
    scores = tf.tensordot(client_embedding, lawyer_embeddings, axes=[[1], [1]])
    scores = tf.squeeze(scores)

    # Ensure that k does not exceed the length of scores
    k = min(k, scores.shape[0])

    # Find the top K indexes
    top_k_indexes = tf.math.top_k(scores, k=k).indices.numpy()

    # Map back to lawyer IDs
    reverse_lawyer_ids_mapping = {v: k for k, v in lawyer_ids_mapping.items()}
    recommended_lawyer_ids = [reverse_lawyer_ids_mapping[idx] for idx in top_k_indexes]

    # Fetch full lawyer data based on recommended lawyer IDs
    recommended_lawyers = lawyers_df[lawyers_df['lawyer_id'].isin(recommended_lawyer_ids)]

    return recommended_lawyers
recommend_lawyers_using_collaborative_filtering(model, client_id, lawyer_ids_mapping)
# Test the recommendation function with a client ID
test_client_id = "-NxGiqasARpkAjrcLrIA"  # Replace with an actual client ID
hyb_recommended_lawyers = recommend_lawyers_hybrid(test_client_id)
print("Recommended Lawyers:\n", hyb_recommended_lawyers)

# Example: Get recommendations for a specific client
client_id = "-NxEEYsulw8bnsz13uaS" # Replace with a valid client ID from your dataset

print("/////////Combined recommendations")


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
            cf_recommended_lawyers = recommend_lawyers_using_collaborative_filtering(model, client_id, lawyer_ids_mapping)
            # Combine both sets of recommendations
            combined_recommendations = pd.concat([hyb_recommended_lawyers, cf_recommended_lawyers])
            # Drop duplicates based on 'lawyer_id'
            combined_recommendations = combined_recommendations.drop_duplicates(subset=['lawyer_id'])
        except ValueError as e:
            logger.info(f"Client ID not found in collaborative filtering dataset: {e}")
    
    return combined_recommendations

# Test the function
client_id = "-NwPoqyls9TBwvD_cKSH"  # Replace with a valid client ID
combined_recommendations = combined_recommendation(client_id)
print("Combined Unique Recommended Lawyers:")
print(combined_recommendations)

@app.route("/recommendations", methods=["GET"])
def get_recommendations():
    client_id = request.args.get("client_id")
    if not client_id:
        return jsonify({"error": "client_id is required"}), 400

    try:
        recommendations = combined_recommendation(client_id)
        result = recommendations.to_dict(orient="records")
        return jsonify({"recommended_lawyers": result}), 200
    except Exception as e:
        logger.error(f"Error while getting recommendations for client_id {client_id}: {e}")
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(port=7000)