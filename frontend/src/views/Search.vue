<template>
  <section class="searchFrame">
    <!-- mobile -->
    <div class="searchResultFrame">
      <div class="searchResultList">
        <form @submit.prevent="Allsearch" class="search_input">
          <div class="inputframe">
            <label for="search"></label>
            <input
              required
              type="text"
              id="search"
              placeholder="검색어를 입력해 주세요"
              v-model.trim="keyword"
            />
          </div>
          <i @click="keywordClear" class="icon-cancel fas fa-times"></i>
          <i @click="Allsearch" class="icon-search fas fa-search"></i>
        </form>
      </div>
      <div class="searchResultList">
        <div class="searchFilterList">
          <header class="seartchFilgerHeader">
            검색 결과
          </header>
          <div class="filters">
            <div class="searchFilter">
              <a
                @click="filterchange1"
                :class="[filter === 1 ? 'active' : '']"
                class="searchFilterTilte"
              >
                모두
              </a>
            </div>
            <div class="searchFilter">
              <a
                @click="filterchange2"
                :class="[filter === 2 ? 'active' : '']"
                class="searchFilterTilte"
              >
                게시물
              </a>
            </div>
            <div class="searchFilter">
              <a
                @click="filterchange3"
                :class="[filter === 3 ? 'active' : '']"
                class="searchFilterTilte"
              >
                사람
              </a>
            </div>
            <div class="searchFilter">
              <a
                @click="filterchange4"
                :class="[filter === 4 ? 'active' : '']"
                class="searchFilterTilte"
              >
                그룹
              </a>
            </div>
          </div>
        </div>
      </div>
      <!-- 컴포넌트 -->
      <transition name="fade">
        <div v-if="loaded" class="searchResultList">
          <!-- <span v-show="filter === 1">
            <GroupResults :category="category_group" />
          </span> -->
          <span v-if="filter === 1">
            <GroupResults
              :groupresults="search_result_all.userList.body"
              :category="category_person"
            />
          </span>
          <span v-if="filter === 1 || filter === 2">
            <SerachResult
              v-for="(result, idx) in search_result_feed"
              :key="idx"
              :result="result"
            />
          </span>
          <span v-if="filter === 3">
            <FilterGroup
              v-for="(grouplist, idx) in search_result_user"
              :key="idx"
              :grouplist="grouplist"
            />
          </span>
          <span v-if="filter === 4">
            <GroupResult
              v-for="(grouplist, idx) in search_result_group"
              :key="idx"
              :grouplist="grouplist"
            />
          </span>
        </div>
      </transition>
      <!-- <div >검색 중 ....</div> -->

      <article v-if="loading" class="spiner">
        <div style="width:100%" class="loading-img-frame">
          <img
            src="@/assets/searching.svg"
            alt=""
            width="50%"
            id="searching-img"
          />
        </div>
      </article>

      <div v-if="empty_search" class="empty-result">
        <div class="empty-result-title">검색 결과가 없습니다</div>
        <img src="@/assets/img/emptysearch.svg" alt="" />
      </div>
    </div>
    <!-- web 검색 결과 list -->
  </section>
</template>

<script>
import GroupResults from "@/components/Search/GroupResults.vue";
import SerachResult from "../components/Search/SerachResult.vue";
import FilterGroup from "../components/Search/FilterGroup.vue";
import { searchall, searchfeed, searchuser } from "@/api/search.js";
import GroupResult from "../components/Search/GroupResult.vue";

export default {
  components: { GroupResults, SerachResult, FilterGroup, GroupResult },
  data() {
    return {
      loading: false,
      loaded: true,
      empty_search: false,
      search_result: [],
      search_result_all: {
        feedList: {
          headers: {},
          body: [],
          statusCode: "OK",
          statusCodeValue: 200,
        },
        userList: {
          headers: {},
          body: [
            {
              id: null,
              nickname: "",
              img: null,
            },
          ],
          statusCode: "OK",
          statusCodeValue: 200,
        },
      },
      search_result_user: [
        {
          id: null,
          img: null,
          nickname: "",
        },
      ],
      search_result_feed: [
        {
          indoorResponseDtoList: [
            {
              content: "",
              date: "",
              file: "",
              id: 0,
              likeCnt: 0,
              tags: [""],
              user: {
                id: 0,
                img: null,
                nickname: "",
              },
            },
          ],
          tagName: "string",
        },
      ],
      search_result_group: [],
      category_group: "그룹",
      category_person: "사람",
      filter: 1,
      keyword: "",
    };
  },
  methods: {
    Allsearch() {
      if (this.keyword === "") {
        alert("검색어를 입력해주세요");
        return;
      }
      this.loading = true;
      this.loaded = false;
      this.empty_search = false;
      setTimeout(() => {
        if (this.filter === 1) {
          searchall(
            this.keyword,
            (res) => {
              console.log(res.data);
              if (
                res.data.feedList.body.length === 0 &&
                res.data.userList.body.length === 0
              ) {
                this.loaded = false;
                this.loading = false;
                this.empty_search = true;
              } else {
                this.search_result_all = res.data;
                this.search_result_feed = res.data.feedList.body;
                console.log(this.search_result_all);
                this.loaded = true;
                this.loading = false;
              }
            },
            (err) => {
              console.log(err);
            }
          );
        } else if (this.filter === 2) {
          searchfeed(
            this.keyword,
            (res) => {
              console.log(res);
              if (res.data.length === 0) {
                this.loaded = false;
                this.loading = false;
                this.empty_search = true;
              } else {
                this.search_result_feed = res.data;
                this.loaded = true;
                this.loading = false;
              }
            },
            (err) => {
              console.log(err);
            }
          );
        } else if (this.filter === 3) {
          searchuser(
            this.keyword,
            (res) => {
              console.log(res);
              if (res.data.length === 0) {
                this.loaded = false;
                this.loading = false;
                this.empty_search = true;
              } else {
                this.search_result_user = res.data;
                this.loaded = true;
                this.loading = false;
              }
            },
            (err) => {
              console.err(err);
            }
          );
        }
      }, 2000);
    },
    keywordClear() {
      this.keyword = "";
    },
    checkquery() {
      if (this.$route.params.filter === "all") {
        this.filter = 1;
        if (this.$route.query.query === "") {
          this.loaded = false;
        } else {
          this.keyword = this.$route.query.query;
          this.Allsearch();
        }
      } else if (this.$route.params.filter === "feed") {
        this.filter = 2;
        if (this.$route.query.query === "") {
          this.loaded = false;
        } else {
          this.keyword = this.$route.query.query;
          this.Allsearch();
        }
      } else if (this.$route.params.filter === "user") {
        this.filter = 3;
        if (this.$route.query.query === "") {
          // 검색어를 입력해주세요
        } else {
          this.keyword = this.$route.query.query;
          this.Allsearch();
        }
      } else if (this.$route.params.filter === "group") {
        this.filter = 4;
        if (this.$route.query.query === "") {
          // 검색어를 입력해주세요
        } else {
          this.keyword = this.$route.query.query;
          this.Allsearch();
        }
      }
    },
    filterchange1() {
      if (this.keyword === "") {
        this.$router
          .push({
            name: "Search",
            params: { filter: "all" },
            query: { query: "" },
          })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      } else {
        this.$router
          .push({
            name: "Search",
            params: { filter: "all" },
            query: { query: this.keyword },
          })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      }
    },
    filterchange2() {
      if (this.keyword === "") {
        this.$router
          .push({
            name: "Search",
            params: { filter: "feed" },
            query: { query: "" },
          })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      } else {
        this.$router
          .push({
            name: "Search",
            params: { filter: "feed" },
            query: { query: this.keyword },
          })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      }
    },
    filterchange3() {
      if (this.keyword === "") {
        this.$router
          .push({
            name: "Search",
            params: { filter: "user" },
            query: { query: "" },
          })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      } else {
        this.$router
          .push({
            name: "Search",
            params: { filter: "user" },
            query: { query: this.keyword },
          })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      }
    },
    filterchange4() {
      if (this.keyword === "") {
        this.$router
          .push({
            name: "Search",
            params: { filter: "group" },
            query: { query: "" },
          })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      } else {
        this.$router
          .push({
            name: "Search",
            params: { filter: "group" },
            query: { query: this.keyword },
          })
          .catch((err) => {
            if (err.name === "NavigationDuplicated") {
              location.reload();
            }
          });
      }
    },
  },
  created() {
    this.checkquery();
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}

#search {
  border: none;
  width: 100%;
  height: 80%;
  font-size: 20px;
}
.icon-cancel {
  font-size: 20px;
  margin: 5px;
  color: darkgray;
}
.icon-search {
  font-size: 18px;
  margin: 5px;
}

.icon-cancel:hover {
}

.icon-search:hover {
  color: rgb(68, 139, 233);
}

.empty-result {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 48px;
}
.empty-result-title {
  width: auto;
  font-size: 25px;
  font-weight: 800;
  margin: 0 24px;
}
.empty-result > img {
  width: 20%;
}

.search_input {
  width: 100%;
  height: 60px;
  border: none;
  background-color: white;
  border-radius: 30px;
  display: flex;
  justify-content: space-between;
  padding: 0 12px;
  align-items: center;
  box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.2);
}

.inputframe {
  width: 100%;
  height: 80%;
  padding-left: 12px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}
input:focus {
  outline: none;
}

.searchFrame {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  background-color: whitesmoke;
}
.filters {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.searchFilterList {
  width: 100%;
  height: auto;
  padding: 0;
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-start;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.2);
}
.seartchFilgerHeader {
  width: 328px;
  margin: 12px;
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
  /* background-color: rebeccapurple; */
  font-size: 25px;
  font-weight: 900;
}

.searchFilterField {
  width: 20%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 18px;
  font-weight: 700;
}

.searchFilter {
  width: 25%;
  height: 55px;
  /* padding: 0 16px; */
  cursor: pointer;
}
.searchFilterTilte.active {
  background-color: #f7eace;
  font-weight: 900;
}

.searchFilterTilte {
  width: 100%;
  height: 100%;
  padding: 0 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 5px;
  font-size: 18px;
  font-weight: 550;
}

.searchFilterTilte:hover {
  background-color: #d1d1d1;
}

.searchResultFrame {
  width: 65%;
  margin: 0 auto;
  padding-top: 80px;
}
.searchResultList {
  width: 100%;
  padding: 16px;
}

.loading-img-frame {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.loading-img-frame > img {
  width: 20%;
}

article > div {
  width: 20%;
  height: auto;
  margin: 2vw;
  animation: bounce 1s 0.5s ease-in-out infinite;
}

@keyframes bounce {
  100% {
    transform: scale(1);
  }
  75% {
    transform: scale(0.6);
  }
}
.spiner {
  margin-top: 20px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

@media screen and (max-width: 768px) {
  .loading-text {
    font-size: 18px;
  }
  .empty-result-title {
    width: auto;
    font-size: 15px;
    margin: 0 24px;
  }
}
</style>
