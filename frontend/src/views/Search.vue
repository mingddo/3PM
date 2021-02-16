<template>
  <div>
    <div class="newsfeed-body">
      <Sidebar />
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
        </div>
        <!-- 컴포넌트 -->
        <transition name="fade">
          <div v-if="loaded" class="searchResultList">
            <!-- <span v-show="filter === 1">
            <GroupResults :category="category_group" />
          </span> -->
            <span v-if="filter === 1">
              <GroupResults
                v-if="search_result_all.userList.body"
                :groupresults="search_result_all.userList.body"
                :category="category_person"
              />
              <GroupResults
                v-if="search_result_all.groupList.body"
                :groupresults="search_result_all.groupList.body"
                :category="category_group"
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
        <!-- web 검색 결과 list -->
      </section>
    </div>
  </div>
</template>

<script>
import GroupResults from "@/components/Search/GroupResults.vue";
import SerachResult from "../components/Search/SerachResult.vue";
import FilterGroup from "../components/Search/FilterGroup.vue";
import {
  searchall,
  searchfeed,
  searchuser,
  searchgroup,
} from "@/api/search.js";
import GroupResult from "../components/Search/GroupResult.vue";
import Sidebar from "@/components/Common/Sidebar.vue";

export default {
  components: { Sidebar, GroupResults, SerachResult, FilterGroup, GroupResult },
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
      if (this.$route.query.query === "") {
        console.log("쿼리가 없고 검색어가 있을 때");
        this.$router.push({
          name: "Search",
          params: { filter: "all" },
          query: { query: this.keyword },
        });
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
                res.data.userList.body.length === 0 &&
                res.data.groupList.body.length === 0
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
        } else if (this.filter === 4) {
          searchgroup(
            this.keyword,
            (res) => {
              console.log("그룹 검색", res);
              if (res.data.length === 0) {
                this.loaded = false;
                this.loading = false;
                this.empty_search = true;
              } else {
                this.search_result_group = res.data;
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

<style scoped></style>
