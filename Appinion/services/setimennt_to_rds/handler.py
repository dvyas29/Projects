from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy import Column, Integer, String, DateTime, Float
from sqlalchemy.ext.declarative import declarative_base

import base64
import json

import datetime

Base = declarative_base()

#{'id': '1420480340381118464', 'username': 'Wyatt Derp 🖐️ 🖐️', 'screen_name': 'WyattDerpy', 'tweet': "b'RT @orlandoribbons: If you would like an Orlando Ribbon,send a SASE to:\\n\\nOrlando Ribbons\\nPOBox 541582\\nOrlando FL 32854\\n\\nIll mail you one.'", 'keyword': 'VirginGalactic', 'polarity': 0.0}


class ModelBase(Base):

    __abstract__ = True

    id = Column(String(256), primary_key=True)
    date_created = Column(DateTime, default=datetime.datetime.utcnow)
    date_modified = Column(DateTime, default=datetime.datetime.utcnow)


class SentimentResult(ModelBase):

    __tablename__ = 'sentiment_results'

    keyword = Column(String(128), nullable=False)
    username = Column(String(128), nullable=False)
    tweet = Column(String(500), nullable=False)
    polarity = Column(Float, nullable=False)

    def __init__(self, id, keyword, username, tweet, polarity):
        self.id = id
        self.keyword = keyword
        self.username = username
        self.tweet = tweet
        self.polarity = polarity

    def __repr__(self):
        return '<User %r>' % (self.keyword)


def lambda_handler(event, context):

    engine = create_engine(
        f'postgresql://postgres:QyTa6993JeFLtPxU@database-1.ctj86imnhzqm.us-east-1.rds.amazonaws.com:5432/sentiment',
        convert_unicode=True)
    db = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=engine))

    records = event['Records']

    for record in records:
        encoded_tweet = record['kinesis']['data']
        decoded_tweet = base64.b64decode(encoded_tweet)
        tweet = json.loads(decoded_tweet)

        sentiment_result = SentimentResult(tweet['id'], tweet['keyword'], tweet['username'], tweet['tweet'], tweet['polarity'])

        print(sentiment_result)

        db.add(sentiment_result)
        db.commit()


# if __name__ == '__main__':
    # lambda_handler({'Records': [{'kinesis': {'kinesisSchemaVersion': '1.0', 'partitionKey': 'AstroPotamus', 'sequenceNumber': '49620572859189540128062800195378749228543795748417830914', 'data': 'eyJpZCI6ICIxNDIwNTg3NzUzMDg4MDQ1MDYyIiwgInVzZXJuYW1lIjogIkFzdHJvcG90YW11cyIsICJzY3JlZW5fbmFtZSI6ICJBc3Ryb1BvdGFtdXMiLCAidHdlZXQiOiAiYidSVCBAZGFpbHlfaG9wcGVyOiBCcmF2ZSBuZXcgd29ybGRcXG5cXG4oUGxlYXNlIGV4Y3VzZSB0aGUgdG9pbGV0IHBhcGVyIGNhbnZhcywgb24gdmFjYXRpb24pXFxuXFxuI3NwYWNlICNzcGFjZXggI0JsdWVPcmlnaW4gI0plZmZCZXpvcyAjVmlyZ2luR2FsYWMnIiwgImtleXdvcmQiOiAiVmlyZ2luR2FsYWN0aWMiLCAicG9sYXJpdHkiOiAwLjIxMzI1NzU3NTc1NzU3NTc2fQ==', 'approximateArrivalTimestamp': 1627529585.074}, 'eventSource': 'aws:kinesis', 'eventVersion': '1.0', 'eventID': 'shardId-000000000000:49620572859189540128062800195378749228543795748417830914', 'eventName': 'aws:kinesis:record', 'invokeIdentityArn': 'arn:aws:iam::493128305655:role/service-role/sentiment_upload-role-vcgewfq3', 'awsRegion': 'us-east-1', 'eventSourceARN': 'arn:aws:kinesis:us-east-1:493128305655:stream/appinion-data-stream'}]}, None)

{
    "Records": [{"kinesis": {"kinesisSchemaVersion": "1.0", "partitionKey": "AstroPotamus", "sequenceNumber": "49620572859189540128062800195378749228543795748417830914", "data": "eyJpZCI6ICIxNDIwNTg3NzUzMDg4MDQ1MDYyIiwgInVzZXJuYW1lIjogIkFzdHJvcG90YW11cyIsICJzY3JlZW5fbmFtZSI6ICJBc3Ryb1BvdGFtdXMiLCAidHdlZXQiOiAiYidSVCBAZGFpbHlfaG9wcGVyOiBCcmF2ZSBuZXcgd29ybGRcXG5cXG4oUGxlYXNlIGV4Y3VzZSB0aGUgdG9pbGV0IHBhcGVyIGNhbnZhcywgb24gdmFjYXRpb24pXFxuXFxuI3NwYWNlICNzcGFjZXggI0JsdWVPcmlnaW4gI0plZmZCZXpvcyAjVmlyZ2luR2FsYWMnIiwgImtleXdvcmQiOiAiVmlyZ2luR2FsYWN0aWMiLCAicG9sYXJpdHkiOiAwLjIxMzI1NzU3NTc1NzU3NTc2fQ==", "approximateArrivalTimestamp": 1627529585.074}, "eventSource": "aws:kinesis", "eventVersion": "1.0", "eventID": "shardId-000000000000:49620572859189540128062800195378749228543795748417830914", "eventName": "aws:kinesis:record", "invokeIdentityArn": "arn:aws:iam::493128305655:role/service-role/sentiment_upload-role-vcgewfq3", "awsRegion": "us-east-1", "eventSourceARN": "arn:aws:kinesis:us-east-1:493128305655:stream/appinion-data-stream"}]
}
