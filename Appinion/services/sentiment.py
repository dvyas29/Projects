import json
import boto3


# Reference 1 - https://docs.aws.amazon.com/lambda/latest/dg/with-s3-example.html

s3 = boto3.client('s3')
def lambda_handler(event, context):


    # Take in input via. S3. integration - access .tweet property of payload

    # DO NOT LEAVE AS IS - Reference 1
    # Get the object from S3 - These lines change depending on how our S3 is setup
    eventName = event['Records'][0]['s3']['bucket']['name']
    eventKey = urllib.parse.unquote_plus(event['Records'][0]['s3']['object']['key'], encoding='ascii')


    try:
        payload = s3.get_object(Bucket=eventName, Key=eventKey)
        print("The Original Tweet was: " + payload['tweet'])
        content = payload['tweet']

    except Exception as e:
        print(e)
        print('Error getting object {} from bucket {}. Make sure they exist and your bucket is in the same region as this function.'.format(key, bucket))
        raise e


    # ---

    # Perform Sent. Analysis on the ascii that has been retrieved.
    # 'content' should contain the text to analyze

    try:
        sentRating = 'Neutral'
        fullTweet = content
        potentialPos = 'good'
        portentialNeg = 'bad'

        if fullTweet.find(potentialPos) != -1:
            sentRating = 'Positive'
        elif fullTweet.find(potentialPos) != -1:
            sentRating = 'Negative'

    except Exception as e:
        print(e)
        print('error rating the tweet'.format(key, bucket))
        raise e

    # Return Rating
    print("The Original Tweet was: " + sentRating)

    return {
        'statusCode': 200,
        'body': json.dumps('Hello from Lambda!')
    }
