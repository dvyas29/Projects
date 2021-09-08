import datetime
from sqlalchemy import Column, String, DateTime, Float
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()


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
