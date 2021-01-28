import axios from 'axios'
// API_KEY 받아오고
// const  API_BASE_URL = `http://172.30.1.47:8080/`
const  API_BASE_URL = `http://ubuntu@i4b208.p.ssafy.io:8080/`

function createInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
  });
  return instance;
}

export { createInstance }