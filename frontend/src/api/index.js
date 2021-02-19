import axios from 'axios'
// API_KEY 받아오고
const  API_BASE_URL = `http://i4b208.p.ssafy.io/api/`
  
function createInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
  });
  return instance;
}

function AuthorizationInstance () {

  const instance = axios.create({
    baseURL: API_BASE_URL,
  });

  instance.interceptors.request.use(
    async config => {
      const vuex_data = localStorage.getItem('vuex');
      const accToken = vuex_data ? JSON.parse(vuex_data).authToken : null;
      config.headers = { 
        'Authorization': accToken,
      }
      return config;
    },
    error => {
      Promise.reject(error)
    }
  )

  instance.interceptors.response.use((response) => {
    return response
  }, async function (error) {
    const errorAPI = error.config;
    if (error.response.data.result === 'expire' || error.response.data.result === 'fail') {
      errorAPI.retry = true;
      await updateAccToken()
      .then((res)=> {
        const authToken = res.data.result
        const vuex_data =  JSON.parse(localStorage.getItem('vuex'));
        vuex_data['authToken'] = authToken
        localStorage.setItem('vuex',JSON.stringify(vuex_data))
        errorAPI.headers["Authorization"] = authToken
      })
      return axios(errorAPI)
   }
    else if (error.response.status === 404 || error.response.status=== 505 ) {
      // window.location.replace(`${window.location.origin/404}`);
      console.log('여기에서 404로 리다이렉트!')
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