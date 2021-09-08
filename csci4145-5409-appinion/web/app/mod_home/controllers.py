from flask import Blueprint, render_template
from app.database import db
from app.mod_sentimentrequest.models import SentimentRequest

mod_home = Blueprint('home', __name__, url_prefix='/home')


@mod_home.route('/', methods=['GET', 'POST'])
def home():
    sentiment_requests = db.query(SentimentRequest).filter_by(
            by_user_email='email@email.com')
    return render_template(
            "home/home.html", sentiment_requests=sentiment_requests)
