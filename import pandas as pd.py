import pandas as pd
import numpy as np
import tensorflow as tf
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler

# Generate sample lawyer data for 100 lawyers
num_lawyers = 100
lawyers_data = {
    'lawyer_id': [str(i) for i in range(1, num_lawyers + 1)],
    'rating': np.random.uniform(1, 5, size=num_lawyers).round(2)
}
lawyers_df = pd.DataFrame(lawyers_data)

# Generate sample client data for 100 clients
num_clients = 100
clients_data = {
    'client_id': [str(i) for i in range(1, num_clients + 1)],
}
clients_df = pd.DataFrame(clients_data)

# Combine the lawyer and client data
combined_data = pd.merge(clients_df, lawyers_df, how='cross')

# Split data into train and test set
train_data, test_data = train_test_split(combined_data, test_size=0.2, random_state=42)

# Define the model
class LawyerMatchingModel(tf.keras.Model):
    def __init__(self, num_users, num_lawyers, embedding_dim):
        super(LawyerMatchingModel, self).__init__()
        self.user_embedding = tf.keras.layers.Embedding(num_users, embedding_dim)
        self.lawyer_embedding = tf.keras.layers.Embedding(num_lawyers, embedding_dim)
        self.dot_product = tf.keras.layers.Dot(axes=1)

    def call(self, inputs):
        user_id, lawyer_id = inputs
        user_emb = self.user_embedding(user_id)
        lawyer_emb = self.lawyer_embedding(lawyer_id)
        dot_product = self.dot_product([user_emb, lawyer_emb])
        return dot_product

# Preprocess data and create datasets
scaler = StandardScaler()
train_data[['rating']] = scaler.fit_transform(train_data[['rating']])
test_data[['rating']] = scaler.transform(test_data[['rating']])

user_ids = train_data['client_id'].astype('category').cat.codes.values
lawyer_ids = train_data['lawyer_id'].astype('category').cat.codes.values
ratings = train_data['rating'].values

test_user_ids = test_data['client_id'].astype('category').cat.codes.values
test_lawyer_ids = test_data['lawyer_id'].astype('category').cat.codes.values
test_ratings = test_data['rating'].values

# Initialize and compile the model
embedding_dim = 32
num_users = len(combined_data['client_id'].unique())
num_lawyers = len(combined_data['lawyer_id'].unique())

model = LawyerMatchingModel(num_users, num_lawyers, embedding_dim)
model.compile(optimizer='adam', loss='mean_squared_error')

# Train the model
model.fit([user_ids, lawyer_ids], ratings, epochs=3, validation_data=([test_user_ids, test_lawyer_ids], test_ratings))

# Make predictions
new_client_id = "1"
new_client_idx = combined_data['client_id'].astype('category').cat.codes[combined_data['client_id'] == new_client_id].values[0]

lawyer_ids_array = np.arange(num_lawyers)
user_ids_array = np.full_like(lawyer_ids_array, new_client_idx)

predictions = model.predict([user_ids_array, lawyer_ids_array])
recommended_lawyers = np.argsort(predictions[:, 0])[::-1][:10]

print(f"Recommendations for client {new_client_id}: {combined_data['lawyer_id'].iloc[recommended_lawyers]}")
