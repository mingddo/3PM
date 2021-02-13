import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function searchall(keyword,success,fail){
  instance
    .get(`search/all`, {
      params: {
        keyword: keyword
      }
    } )
    .then(success)
    .catch(fail)
}

function searchfeed(keyword,success,fail){
  instance
    .get(`search/feed`, {
      params: {
        keyword: keyword
      }
    } )
    .then(success)
    .catch(fail)
}

function searchuser(keyword,success,fail){
  instance
    .get(`search/user`, {
      params: {
        keyword: keyword
      }
    } )
    .then(success)
    .catch(fail)
}

export {
  searchall,
  searchfeed,
  searchuser
}