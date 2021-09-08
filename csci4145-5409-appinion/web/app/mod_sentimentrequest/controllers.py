import requests
from flask import (
        Blueprint,
        request,
        render_template,
        session,
        redirect,
        url_for
    )
from app.database import db

from app.mod_sentimentrequest.models import SentimentRequest
from app.mod_sentimentrequest.forms import SentimentRequestForm

mod_sentimentrequest = Blueprint(
        'sentimentrequest', __name__, url_prefix='/sentimentrequest')


@mod_sentimentrequest.route('/', methods=['GET', 'POST'])
def sentimentrequest():
    form = SentimentRequestForm(request.form)

    if form.validate_on_submit():
        sentiment_request = SentimentRequest(
                form.keyword.data, 'email@email.com')

        # Trigger the sentiment analysis
        result = requests.get(f'https://gg9qu1nns2.execute-api.us-east-1.amazonaws.com/default/tweet_capture?keyword={form.keyword.data}', headers={'x-api-key': 'AX8x81eUu05wZzb0qhOjx7LMMkvASE8r2S7W373F'})
        print(result)

        db.add(sentiment_request)
        db.commit()

        return redirect(url_for('home.home'))

    return render_template('sentimentrequest/sentimentrequest.html', form=form)
