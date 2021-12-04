from flask import Flask,request,jsonify
import pickle
import numpy as np

model=pickle.load(open('lr_model.pkl','rb'))

app=Flask(__name__)
@app.route('/')
def home():
    return "Hello world"
@app.route('/predict',methods=['POST'])
def predict():
    Open=request.form.get('Open')
    Low=request.form.get('Low')
    High=request.form.get('High')
    Volume=request.form.get('Volume')

    input_query=np.array([[Open,Low,High,Volume]])
    result=model.predict(input_query)[0]

    # result={'Open':Open,'Low':Low,'High':High,'Volume':Volume}
    return jsonify(result)
if __name__=='__main__':
    app.run(debug=True)