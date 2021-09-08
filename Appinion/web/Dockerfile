FROM tiangolo/uwsgi-nginx-flask:python3.8-alpine

EXPOSE 80

WORKDIR /var/www/
COPY ./app /var/www/app
COPY ./config.py /var/www
COPY ./requirements.txt /var/www/
COPY ./main.py /var/www
COPY ./uwsgi.ini /var/www

RUN \
 apk add --no-cache postgresql-libs && \
 apk add --no-cache --virtual .build-deps gcc musl-dev postgresql-dev build-base && \
 pip install -r /var/www/requirements.txt && \
 apk --purge del .build-deps
