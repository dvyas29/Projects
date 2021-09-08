import os

DEBUG = True

# Define the application directory
BASE_DIR = os.path.abspath(os.path.dirname(__file__))

# Threads
THREADS_PER_PAGE = 2

# Enable protection against *Cross-site Request Forgery (CSRF)*
CSRF_ENABLED = True


# These should be stored elsewhere for prod

# Use a secure, unique and absolutely secret key for
# signing the data.
CSRF_SESSION_KEY = "XVjw39PiOBI6pxPlkWLj"

# Secret key for signing cookies
SECRET_KEY = "m04kUvgNCGul89yWx9QX"

SQLALCHEMY_TRACK_MODIFICATIONS = False
