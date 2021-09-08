from flask import Blueprint, render_template, request
from app.database import db
from app.mod_results.models import SentimentResult

mod_results = Blueprint('results', __name__, url_prefix='/results')


@mod_results.route('/', methods=['GET'])
def result():
    keyword = request.args.get('keyword', default='HWAT', type=str)
    sentiment_results = db.query(SentimentResult).filter_by(
            keyword=keyword)
    avrg_pol = 0
    standing = 'Neutral'
    av = False

    if sentiment_results.count() > 0:
        av = True
        for result in sentiment_results:
            avrg_pol += result.polarity

        avrg_pol = avrg_pol/sentiment_results.count()


        if avrg_pol < 0:
            standing = 'Bad'

        if avrg_pol > 0:
            standing = 'Good'

    return render_template("results/results.html", sentiment_results=sentiment_results, standing=standing, av=av)
