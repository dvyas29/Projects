import datetime
from sqlalchemy import Column, Integer, String, DateTime
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()


class ModelBase(Base):

    __abstract__ = True

    id = Column(Integer, primary_key=True)
    date_created = Column(DateTime, default=datetime.datetime.utcnow)
    date_modified = Column(DateTime, default=datetime.datetime.utcnow)


class SentimentRequest(ModelBase):

    __tablename__ = 'sentiment_requests'

    keyword = Column(String(128), nullable=False)
    by_user_email = Column(String(128), nullable=False)

    def __init__(self, keyword, by_user_email):

        self.keyword = keyword
        self.by_user_email = by_user_email

    def __repr__(self):
        return '<User %r>' % (self.keyword)
