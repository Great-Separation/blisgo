# Vault 접속을 위한 토큰 생성
HCP_API_TOKEN=$(curl --location "https://auth.idp.hashicorp.com/oauth2/token" \
--header "Content-Type: application/x-www-form-urlencoded" \
--data-urlencode "client_id=$HCP_CLIENT_ID" \
--data-urlencode "client_secret=$HCP_CLIENT_SECRET" \
--data-urlencode "grant_type=client_credentials" \
--data-urlencode "audience=https://api.hashicorp.cloud" | jq -r .access_token)

# Vault에서 JSON 값을 받아옴
VAULT_JSON=$(curl --location "https://api.cloud.hashicorp.com/secrets/2023-06-13/organizations/1f81c43a-c020-4e89-9fff-a97504adda1c/projects/705e4885-3d57-4917-a218-1151c919b909/apps/blisgo/open" \
--request GET \
--header "Authorization: Bearer $HCP_API_TOKEN")

# JSON에서 필요한 값을 추출하고 이를 환경변수로 설정
for row in $(echo "${VAULT_JSON}" | jq -r '.secrets[] | @base64'); do
    _jq() {
     echo ${row} | base64 --decode | jq -r ${1}
    }

   key=$(_jq '.name')
   value=$(_jq '.version.value')

   export $key=$value

   # 설정된 환경변수의 키-값 쌍 출력
   echo "$key=$value"
done