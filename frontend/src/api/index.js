// API_KEY 받아오고
const  API_BASE_URL = `http://172.30.1.60:8080/`

function createInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
  });
  return instance;
}

export { createInstance }