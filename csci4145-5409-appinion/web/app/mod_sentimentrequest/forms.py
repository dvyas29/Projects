from flask_wtf import FlaskForm
from wtforms import TextField
from wtforms.validators import Required


class SentimentRequestForm(FlaskForm):
    keyword = TextField(
            'Keyword',
            [Required(message='Must provide a keyword')])
