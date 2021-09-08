from flask import Flask, render_template, redirect
from app.mod_home.controllers import mod_home as home_module
from app.mod_auth.controllers import mod_auth as auth_module
from app.mod_results.controllers import mod_results as results_module
from app.mod_sentimentrequest.controllers import (
        mod_sentimentrequest as sentimentrequest_module
    )
from .database import db

app = Flask(__name__)

# Configurations
app.config.from_object('config')


@app.route("/")
def home():
    return redirect('/home')


@app.errorhandler(404)
def not_found(error):
    return render_template('errors/404.html'), 404


app.register_blueprint(home_module)
app.register_blueprint(auth_module)
app.register_blueprint(sentimentrequest_module)
app.register_blueprint(results_module)

if __name__ == '__main__':
    with app.app_context():
        db.create_all()
