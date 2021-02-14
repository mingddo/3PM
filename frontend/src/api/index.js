import axios from 'axios'
// import { mapState } from 'vuex'
// API_KEY 받아오고
// const  API_BASE_URL = `http://172.30.1.47:8080/`
// const  API_BASE_URL = `http://172.30.1.47:8080/`
// const  API_BASE_URL = `http://172.30.1.35:8081/`
// const  API_BASE_URL = `http://172.30.1.51:8081/`
let token = null
const  API_BASE_URL = `http://i4b208.p.ssafy.io/api/`
// const  API_BASE_URL = `http://ubuntu@i4b208.p.ssafy.io:8080/`

  
function createInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
  });
  return instance;
}

function AuthorizationInstance () {
  if (localStorage.getItem('vuex') !== null ) {
    let vuex_data = localStorage.getItem('vuex')
    vuex_data = JSON.parse(vuex_data);
    token = vuex_data["authToken"]
  }
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Authorization" : token
    }
  });
  instance.interceptors.response.use((response) => {
    return response
  }, async function (error) {
    const errorAPI = error.config;
    if (error.response.data.result === 'expire' && !errorAPI.retry) {
      console.log('error.response.data.result === expire')
      errorAPI.retry = true;
     // updateAccToken 함수에서 refToken을 헤더로 서버에 요청보내고
     // 응답으로 새로 갱신한 accToken을 받아 headers에 업데이트한다
      await updateAccToken()
      .then((res)=> {
        console.log('updateAccToken')
        const authToken = res.data.result
        const vuex_data =  JSON.parse(localStorage.getItem('vuex'));
        vuex_data['authToken'] = authToken
        localStorage.setItem('vuex',JSON.stringify(vuex_data))
        errorAPI.headers["Authorization"] = authToken
      })
      return axios(errorAPI)
   }
   return Promise.reject(error)
  })
  return instance
  }

function updateAccToken () {
  return new Promise((resolve) => { 
    const vuex_data =  JSON.parse(localStorage.getItem('vuex'));
    const refreshToken = vuex_data["refToken"]
    const headers = {"Authorization" : refreshToken}
    axios.get(`${API_BASE_URL}users/token`, {
      headers,
    })
    .then((res) => {
      resolve(res)
    })
  })
}
export { createInstance, AuthorizationInstance }