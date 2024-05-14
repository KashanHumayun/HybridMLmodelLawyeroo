import firebase_admin
from firebase_admin import credentials, db
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.neighbors import NearestNeighbors
from flask import Flask, jsonify, request
import requests
import pickle
import logging
import socket
from requests.exceptions import RequestException
from google.cloud.exceptions import ServiceUnavailable
import threading
import time
import datetime

# Initialize logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Load Firebase Admin SDK configuration
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

# Retry logic
max_retries = 3
firebase_initialized = False

for attempt in range(max_retries):
    try:
        cred = credentials.Certificate(service_account_key)
        firebase_admin.initialize_app(cred, {
            'databaseURL': 'https://lawyeroo-default-rtdb.asia-southeast1.firebasedatabase.app'
        })
        logger.info("Firebase initialized successfully")
        firebase_initialized = True
        break
    except (socket.gaierror, ServiceUnavailable, RequestException) as e:
        logger.error(f"Firebase initialization failed: {e}. Retrying {attempt + 1}/{maxretries}...")

if not firebase_initialized:
    logger.critical("Failed to initialize Firebase after several attempts. Exiting.")
    raise SystemExit("Could not initialize Firebase. Please check your network connection and try again later.")

# Reference to Realtime Database
ref = db.reference('/')

# Global variables
tfidf = None
knn = None
combined_df = pd.DataFrame(columns=['id', 'preferences', 'preferences_str'])
model_file = "recommendation_model.pkl"
previous_ids = set()

def train_model():
    global tfidf, knn, combined_df
    try:
        if combined_df.empty:
            raise ValueError("Dataframe is empty. Cannot train model.")

        tfidf = TfidfVectorizer(stop_words='english')
        tfidf_matrix = tfidf.fit_transform(combined_df['preferences_str'])
        knn = NearestNeighbors(n_neighbors=5, algorithm='auto').fit(tfidf_matrix)
        logger.info("Model trained successfully")
    except ValueError as ve:
        # Handle empty vocabulary errors gracefully
        logger.error(f"Model training failed due to ValueError: {ve}")
        tfidf = None
        knn = None

    save_model()

def load_model():
    global tfidf, knn, combined_df, previous_ids
    try:
        with open(model_file, "rb") as f:
            tfidf, knn, combined_df, previous_ids = pickle.load(f)
        logger.info("Model loaded successfully")
    except (FileNotFoundError, ValueError) as e:
        logger.error(f"Error loading model: {e}")
        combined_df = pd.DataFrame(columns=['id', 'preferences', 'preferences_str'])
        tfidf = None
        knn = None
        previous_ids = set()

    # Load existing data from Firebase
    load_existing_data()

    # Update the model after loading existing data
    if tfidf is None or knn is None:
        train_model()


def load_existing_data():
    global combined_df, previous_ids
    logger.info("Loading existing data from Firebase...")

    # Load data for lawyers
    lawyers = ref.child('lawyers').get()
    for lawyer_id, data in lawyers.items():
        new_entry = {
            'id': lawyer_id,
            'preferences': data.get('specializations', []),
            'preferences_str': ' '.join(data.get('specializations', []))
        }
        combined_df = pd.concat([combined_df, pd.DataFrame([new_entry])], ignore_index=True)
        previous_ids.add(lawyer_id)

    # Load data for clients
    clients = ref.child('clients').get()
    for client_id, data in clients.items():
        new_entry = {
            'id': client_id,
            'preferences': data.get('preferences', []),
            'preferences_str': ' '.join(data.get('preferences', []))
        }
        combined_df = pd.concat([combined_df, pd.DataFrame([new_entry])], ignore_index=True)
        previous_ids.add(client_id)

    logger.info("Loaded existing data successfully.")

def save_model():
    global tfidf, knn, combined_df, previous_ids
    with open(model_file, "wb") as f:
        pickle.dump((tfidf, knn, combined_df, previous_ids), f)
    logger.info("Model saved successfully")

def update_model(new_entry):
    global tfidf, knn, combined_df, previous_ids

    logger.info(f"Updating model with new entry: {new_entry}")

    new_id = new_entry['id']
    if new_id in previous_ids:
        logger.info(f"Skipping existing entry for id: {new_id}")
        return

    previous_ids.add(new_id)

    # Convert new entry to string for vectorization
    new_entry['preferences_str'] = ' '.join(new_entry['preferences'])

    # Add new entry to dataframe
    combined_df = pd.concat([combined_df, pd.DataFrame([new_entry])], ignore_index=True)

    # Incremental model update
    try:
        tfidf_matrix = tfidf.fit_transform(combined_df['preferences_str'])
        knn.fit(tfidf_matrix)
        logger.info("Model updated incrementally")
    except ValueError as ve:
        logger.error(f"Model update failed due to ValueError: {ve}")
        tfidf = None
        knn = None

    save_model()


def lawyer_listener(event):
    logger.info(f"Lawyer listener triggered with event: {event}")
    if event.data and event.data.get('specializations', []):
        new_entry = {'id': event.path.strip('/'), 'preferences': event.data.get('specializations', [])}
        update_model(new_entry)

def client_listener(event):
    logger.info(f"Client listener triggered with event: {event}")
    if event.data and event.data.get('preferences', []):
        new_entry = {'id': event.path.strip('/'), 'preferences': event.data.get('preferences', [])}
        update_model(new_entry)

# Set up listeners for new lawyers and clients
lawyers_watch = threading.Thread(target=ref.child('lawyers').listen, args=(lawyer_listener,), daemon=True)
clients_watch = threading.Thread(target=ref.child('clients').listen, args=(client_listener,), daemon=True)

lawyers_watch.start()
clients_watch.start()

app = Flask(__name__)

@app.route('/recommend_news', methods=['POST'])
def recommend_news():
    global tfidf, knn, combined_df
    user_id = request.json.get('user_id')
    # api_key = '31a6e9bd038e47d8aa7e555b1d1ce0bd'
    api_key = 'fdf1b308b37c4b1c89b483caaceced1b'
    # Validate global variables
    if tfidf is None or knn is None or combined_df is None:
        logger.error("Recommendation model not initialized properly. tfidf, knn, or combined_df is None.")
        return jsonify({'error': 'Recommendation model not initialized properly'}), 500

    # Validate user_id
    if user_id is None:
        logger.error("No user_id provided in the request.")
        return jsonify({'error': 'No user_id provided'}), 400

    # Find the index of the user in dataframe
    idx = combined_df.index[combined_df['id'] == user_id].tolist()
    if not idx:
        logger.error(f"User ID {user_id} not found in the data.")
        return jsonify({'error': 'User ID not found in the data'}), 404

    idx = idx[0]

    # Find the nearest neighbors (similar users)
    try:
        distances, indices = knn.kneighbors(tfidf.transform([combined_df.iloc[idx]['preferences_str']]), n_neighbors=5)
    except Exception as e:
        logger.error(f"Error during k-nearest neighbors search: {e}")
        return jsonify({'error': 'Error during recommendation process'}), 500

    # Get keywords from similar users' preferences
    keywords = []
    for i in indices[0]:
        keywords.extend(combined_df.iloc[i]['preferences'])

    # Fetch news for these keywords
    all_articles = []
    # Get the current date and time
    now = datetime.datetime.now()

    # Get the date and time three days ago
    three_days_ago = now - datetime.timedelta(days=3)

    # Convert to ISO 8601 format
    from_date = three_days_ago.isoformat()

    for keyword in set(keywords):
        url = f"https://newsapi.org/v2/everything?q={keyword}&from={from_date}&language=en&apiKey={api_key}"
        response = requests.get(url)
        if response.status_code == 200:
            all_articles.extend(response.json()['articles'])
        else:
            logger.warning(f"Failed to fetch news for keyword '{keyword}', status code: {response.status_code}")

    logger.info(f"Returning {len(all_articles[:15])} articles for user ID {user_id}")
    return jsonify({'articles': all_articles[:15]}), 200


# Load the model at the beginning
load_model()

if __name__ == '__main__':
    app.run(debug=True, port=6000)

