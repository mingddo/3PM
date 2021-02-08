import axios from 'axios'
// import { mapState } from 'vuex'
// API_KEY 받아오고
// const  API_BASE_URL = `http://172.30.1.47:8080/`
// const  API_BASE_URL = `http://172.30.1.47:8080/`
// const  API_BASE_URL = `http://172.30.1.35:8081/`
// const  API_BASE_URL = `http://172.30.1.51:8081/`
const  API_BASE_URL = `http://i4b208.p.ssafy.io:8081/api/`
// const  API_BASE_URL = `http://ubuntu@i4b208.p.ssafy.io:8080/`

let vuex_data = localStorage.getItem('vuex')
vuex_data = JSON.parse(vuex_data);
let token = vuex_data["authToken"]
console.log(token)
function createInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
  });
  return instance;
}

function AuthorizationInstance () {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Authorization" : token
    }
  });
  return instance;
}

export { createInstance, AuthorizationInstance }