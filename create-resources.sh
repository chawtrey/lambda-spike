echo "All resources initialized! 🚀"
echo "Create admin"
aws \
 --endpoint-url=http://localhost:4566 \
 iam create-role \
 --role-name admin-role \
 --path / \
 --assume-role-policy-document file:./admin-policy.json
echo "Make S3 bucket"
aws \
  s3 mb s3://lambda-functions \
  --endpoint-url http://localhost:4566
echo "Copy the lambda function to the S3 bucket"
aws \
  s3 cp /lambdas.jar s3://lambda-functions \
  --endpoint-url http://localhost:4566
echo "Create the lambda exampleLambda"
aws \
  lambda create-function \
  --endpoint-url=http://localhost:4566 \
  --function-name exampleLambda \
  --role arn:aws:iam::000000000000:role/admin-role \
  --code S3Bucket=lambda-functions,S3Key=lambdas.jar \
  --handler us.hawtrey.lambdaspike.handler.LambdaHandler \
  --runtime java11 \
  --description "SQS Lambda handler for test sqs." \
  --timeout 60 \
  --memory-size 1024
echo "Create put-rule"
aws \
  events put-rule \
  --name my-scheduled-rule \
  --schedule-expression 'rate(1 minutes)'
echo "Create permission"
aws \
  lambda add-permission \
  --function-name exampleLambda \
  --statement-id my-scheduled-event \
  --action 'lambda:InvokeFunction' \
  --principal events.amazonaws.com \
  --source-arn arn:aws:events:us-east-1:123456789012:rule/my-scheduled-rule
aws \
  events put-targets --rule my-scheduled-rule \
  --targets "Id"="1","Arn": "arn:aws:lambda:us-east-1:123456789012:function:exampleLambda"
