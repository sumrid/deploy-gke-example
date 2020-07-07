[![CircleCI](https://circleci.com/gh/sumrid/deploy-gke-example.svg?style=svg)](https://circleci.com/gh/sumrid/deploy-gke-example)
![](https://img.shields.io/static/v1.svg?label=made%20with&message=JAVA&color=f268b4&logo=java&style=flat)
![](https://img.shields.io/static/v1.svg?label=github&message=Wiki&color=7150aa&logo=github&style=flat)
![](https://img.shields.io/static/v1.svg?label=container&message=Docker&color=1488C6&logo=docker&style=flat&logoColor=white)
# test-soa


เข้า vm
gcloud compute --project "soa-group-7-174" ssh --zone "asia-southeast1-b" "instance-1"

### Project ID
soa-group-7-174


### build docker image
```docker build -t <name>:<tag> .```  
gradle clean build  
docker build -t asia.gcr.io/soa-group-7-174/service-registry .  
docker build -t asia.gcr.io/soa-group-7-174/api-gateway .  
docker build -t asia.gcr.io/soa-group-7-174/bill .  
docker build -t asia.gcr.io/soa-group-7-174/report .      
docker build -t asia.gcr.io/soa-group-7-174/user .  


### push image to container registry
```docer push <name>:<tag>```  
docker push asia.gcr.io/soa-group-7-174/service-registry  
docker push asia.gcr.io/soa-group-7-174/api-gateway  
docker push asia.gcr.io/soa-group-7-174/bill  
docker push asia.gcr.io/soa-group-7-174/report  
docker push asia.gcr.io/soa-group-7-174/user  



### การ deploy
* 1 เชื่อมต่อ cluster ก่อน  
```gcloud container clusters get-credentials soa-service --zone asia-southeast1-b --project soa-group-7-174```

* 2 kubernetes  
```kubectl run {deployment_name} --image=gcr.io/$PROJECT_ID/{name}:{tag} --port={port}```

kubectl run registry --image=asia.gcr.io/soa-group-7-174/service-registry:latest --port=8761  
kubectl run gateway --image=asia.gcr.io/soa-group-7-174/api-gateway:latest --port=80  
kubectl run bill --image=asia.gcr.io/soa-group-7-174/bill:latest --port=80  
kubectl run report --image=asia.gcr.io/soa-group-7-174/report:latest --port=80  
kubectl run user --image=asia.gcr.io/soa-group-7-174/user:latest --port=80  
kubectl run product --image=asia.gcr.io/soa-group-7-174/product:latest --port=80  


### expose app
* 3 external traffic  ให้ออกเน็ตนอกได้  
```kubectl expose deployment {deployment_name} --type="LoadBalancer"```

kubectl expose deployment registry --type="LoadBalancer"  
kubectl expose deployment gateway --type="LoadBalancer" --target-port=8080  
kubectl expose deployment bill --type="LoadBalancer" --target-port=8080  
kubectl expose deployment report --type="LoadBalancer" --target-port=8080  
kubectl expose deployment user --type="LoadBalancer" --target-port=8080  
kubectl expose deployment product --type="LoadBalancer" --target-port=8080  


###  deploy new version
kubectl set image deployment/registry registry=asia.gcr.io/soa-group-7-174/service-registry:latest  
kubectl set image deployment/gateway gateway=asia.gcr.io/soa-group-7-174/api-gateway:latest  
kubectl set image deployment/bill bill=asia.gcr.io/soa-group-7-174/bill:latest  
kubectl set image deployment/report report=asia.gcr.io/soa-group-7-174/report:latest  
kubectl set image deployment/user user=asia.gcr.io/soa-group-7-174/user:latest  
kubectl set image deployment/product product=asia.gcr.io/soa-group-7-174/product:latest  


### resize cluster
เปิด-ปิด cluster 
```gcloud --quiet container clusters resize soa-service --size=0```



## การเชื่อมต่อกับ travis ci
1. เข้า shell ใน gcloud ของเรา
2. ติดตั้ง sudo gem install travis และ Login '>> travis login --pro'
3. อัพโหลด keyfile.json เข้าไปเก็บไว้ใน repo
5. สร้างไฟล์ .travis.yml
4. เข้ารหัส keyfile โดยใช้คำสั่ง
    ">> travis encrypt-file <file.json> --pro"
5. copy "openssl aes-256-cbc...." ไปใส่ใน .travis.yml
6. 'git add <keyfile..> .travis.yml'
