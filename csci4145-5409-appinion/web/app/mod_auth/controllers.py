from flask import Blueprint, render_template

mod_auth = Blueprint('auth', __name__, url_prefix='/auth')


@mod_auth.route('/login', methods=['GET'])
def login():
    return render_template('auth/login.html')
