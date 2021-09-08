import boto3
from tweepy import OAuthHandler, API
import json
import time
from textblob import TextBlob


def lambda_handler(event, context):

    # create the kinesis client

    # Twitter Cred.
    consumer_Key='hcbuw2JmDJ9rtsgLwUHQk2qSk'
    consumer_Secret= 'pfkCU47DDWTS9pRc0SB6uYlunj4iFpjnBIBIKqwJ8wZwQ26iY0'
    access_Token= '801976087-T98hn67TUAsXnGPQIvzViJpKm77XFQwPqJP90ryY'
    access_Token_Secret= '07ralL6wVzBjmRqxeYeVkTYJGTvMXf9Z5Si4fWA4uGRXt'

    keyword = event['queryStringParameters']['keyword']

    # set twitter keys/tokens
    auth = OAuthHandler(consumer_Key, consumer_Secret)
    auth.set_access_token(access_Token, access_Token_Secret)

    api = API(auth)
    data = api.search(q=keyword, count=500)

    try:
        print('Twitter streaming...')

        # create instance of the tweet stream listener
        kinesis_client = boto3.client('kinesis')

        for status in data:

            tweet = status._json

            try:
                blob = TextBlob(tweet['text'])
                payload = {
                    'id': str(tweet['id']),
                    'username': tweet['user']['name'],
                    'screen_name': tweet['user']['screen_name'],
                    'tweet': str(tweet['text'].encode('ascii','ignore')),
                    'keyword': keyword,
                    'polarity': blob.sentiment.polarity
                }

                # only put the record when message is not None
                if (payload):
                    print(payload)
                    # note that payload is a list

                    # Data getting deliverd to Kinesis
                
                    put_response = kinesis_client.put_record(
                        StreamName='appinion-data-stream',
                        Data=json.dumps(payload),
                        PartitionKey=str(tweet['user']['screen_name'])
                    )
            except (AttributeError, Exception) as e:
                print(e)

        return {
                'statusCode': 200,
                'body': json.dumps({})
                }

    except Exception as e:
        print(e)


# if __name__ == '__main__':
    # lambda_handler({'queryStringParameters': {'keyword': 'Trump'}}, None)
