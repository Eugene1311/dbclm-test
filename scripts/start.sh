docker run --name dbclm-test-postgres -e POSTGRES_PASSWORD=postgrespw -e POSTGRES_DB=dbclm_test -p 49153:5432 -d postgres:latest