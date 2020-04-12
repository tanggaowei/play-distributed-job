# play-distributed-job
distributed job for play frameworkd 1.x

1. download dependencies
```
play deps
```

2. run 3 progress, test distributed job
```
play run --http.port=4001
play run --http.port=4002
play run --http.port=4003
```
