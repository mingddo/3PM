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

function searchgroup(keyword,success,fail){
  instance
    .get(`search/group`, {
      params: {
        keyword: keyword
      }
    } )
    .then(success)
    .catch(fail)
}

function autocompleteTag(hashtag,success,fail){
  instance
    .get(`search/auto/tag`, {
      params: {
        hashtag: hashtag
      }
    } )
    .then(success)
    .catch(fail)
}

function autocompleteUser(user_name,success,fail){
  instance
    .get(`search/auto/user`, {
      params: {
        nickname: user_name
      }
    } )
    .then(success)
    .catch(fail)
}

export {
  searchall,
  searchfeed,
  searchuser,
  searchgroup,
  autocompleteUser,
  autocompleteTag
}