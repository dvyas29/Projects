import datetime
from sqlalchemy import Column, Integer, String, DateTime
from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()


class ModelBase(Base):

    __abstract__ = True

    id = Column(Integer, primary_key=True)
    date_created = Column(DateTime, default=datetime.datetime.utcnow)
    date_modified = Column(DateTime, default=datetime.datetime.utcnow)


class User(ModelBase):

    __tablename__ = 'users'

    username = Column(String(128), nullable=False)
    email = Column(String(128), nullable=False)

    def __init__(self, username, email):

        self.username = username
        self.email = email

    def __repr__(self):
        return '<User %r>' % (self.username)
