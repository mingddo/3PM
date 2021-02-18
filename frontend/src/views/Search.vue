<template>
  <div>
    <div class="newsfeed-body">
      <Sidebar />
      <section class="searchFrame">
        <!-- mobile -->
        <div class="searchResultFrame">
          <div
            class="searchResultList"
            @keyup.down="selectValue('down')"
            @keyup.up="selectValue('up')"
            @mouseover="removeValue"
          >
            <form @submit.prevent="Allsearch" class="search_input">
              <div class="inputframe">
                <label for="search"></label>
                <input
                  class="r"
                  required
                  type="text"
                  id="search"
                  placeholder="검색어를 입력해 주세요"
                  v-model.trim="keyword"
                  @input="autoTag"
                  autocomplete="off"
                  ref="search"
                />
              </div>
              <i @click="keywordClear" class="icon-cancel fas fa-times"></i>
              <i @click="Allsearch" class="icon-search fas fa-search"></i>
            </form>
            <ul
              class="r"
              :class="[autotag ? 'autocompleteUl' : 'autocompleteUl active']"
              tabindex="0"
            >
              <li
                class="autoImtem"
                tabindex="-1"
                v-for="(tag, idx) in tags"
                :key="idx"
                @click="changeValue(tag.value)"
                @keyup.enter="selectValue('enter', tag.value)"
              >
                {{ tag.value }}
              </li>
            </ul>
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
                v-if="search_result_all.userList.body.length !== 0"
                :groupresults="search_result_all.userList.body"
                :category="category_person"
              />
              <GroupResults
                v-if="search_result_all.groupList.body.length !== 0"
                :groupresults="search_result_all.groupList.body"
                :category="category_group"
              />
            </span>
            <span v-if="filter === 1 || (filter === 2 && search_result_feed)">
              <SerachResult
                v-for="(result, idx) in search_result_feed"
                :key="idx"
                :result="result"
              />
            </span>
            <span v-if="filter === 3 && search_result_user">
              <FilterGroup
                v-for="(grouplist, idx) in search_result_user"
                :key="idx"
                :grouplist="grouplist"
              />
            </span>
            <span v-if="filter === 4 && search_result_group">
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
  autocompleteUser,
  autocompleteTag,
} from "@/api/search.js";
import GroupResult from "../components/Search/GroupResult.vue";
import Sidebar from "@/components/Common/Sidebar.vue";

// 방향키로 페이지 창 못 움직이게 하는 코드
window.addEventListener(
  "keydown",
  function(e) {
    // space and arrow keys
    if ([38, 40].indexOf(e.keyCode) > -1) {
      e.preventDefault();
    }
  },
  false
);

export default {
  components: {
    Sidebar,
    GroupResults,
    SerachResult,
    FilterGroup,
    GroupResult,
  },
  data() {
    return {
      autotag: false,
      tags: [],
      loading: false,
      loaded: true,
      empty_search: false,
      search_result: [],
      search_result_all: {},
      search_result_user: [],
      search_result_feed: [],
      search_result_group: [],
      category_group: "그룹",
      category_person: "사람",
      filter: 1,
      keyword: "",
    };
  },
  methods: {
    // 인풋 창 검색어 넣고 포커스
    changeValue(str) {
      this.keyword = str;
      this.autotag = false;
      this.tags = [];
      this.removeValue();
      this.$refs.search.focus();
    },
    // 방향키로 태그리스트 이동 및 엔터로 검색창에 반영
    selectValue(keycode, str) {
      let hasClass = false;
      if (document.querySelector(".r").classList != null) {
        hasClass = document.querySelector(".r").classList.contains("key");
      }

      const isTags = this.tags.length != 0;
      if (keycode === "down" && isTags) {
        if (!hasClass) {
          const thisEl = document.querySelectorAll(".r li")[0];
          document.querySelector(".r").classList.add("key");
          thisEl.classList.add("selected-li");
          thisEl.focus();
        } else {
          const lastEl = document.querySelector(".r li:last-child");
          const thisEl = document.querySelector(".r li.selected-li");
          const nextEl = thisEl.nextElementSibling;
          if (!lastEl.classList.contains("selected-li")) {
            thisEl.classList.remove("selected-li");
            nextEl.classList.add("selected-li");
            nextEl.focus();
          }
        }
      }
      if (keycode === "up" && hasClass) {
        const firstEl = document.querySelectorAll(".r li")[0];
        const thisEl = document.querySelector(".r li.selected-li");
        const prevEl = thisEl.previousElementSibling;
        if (!firstEl.classList.contains("selected-li")) {
          thisEl.classList.remove("selected-li");
          prevEl.classList.add("selected-li");
          prevEl.focus();
        } else {
          this.$refs.search.focus();
        }
      }
      if (keycode === "enter" && hasClass) {
        this.changeValue(str);
      }
    },
    // 검색창에서 마우스오버하거나 선택시 클래스리스트 비우기
    removeValue() {
      if (document.querySelector(".r").classList.contains("key")) {
        document.querySelector(".r").classList.remove("key");
        document
          .querySelector(".r li.selected-li")
          .classList.remove("selected-li");
      }
    },

    autoTag(e) {
      if (e.target.value) {
        this.keyword = e.target.value;
        if (this.filter === 1 || this.filter == 2) {
          autocompleteTag(
            this.keyword,
            (res) => {
              if (res.data) {
                this.autotag = true;
                this.tags = res.data;
              }
            },
            (err) => {
              console.error(err);
            }
          );
        } else if (this.filter === 3) {
          autocompleteUser(
            this.keyword,
            (res) => {
              if (res.data) {
                this.autotag = true;
                this.tags = res.data;
              }
            },
            (err) => {
              console.error(err);
            }
          );
        }
      }
    },
    Allsearch() {
      this.$route.query.query = this.keyword;
      this.autotag = false;
      if (this.keyword === "") {
        this.$swal.fire({
          icon: 'error',
          text: '검색어를 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
        return;
      }
      if (this.$route.query.query === "") {
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
              if (
                res.data.feedList.body.length === 0 &&
                res.data.userList.body.length === 0 &&
                res.data.groupList.body.length === 0
              ) {
                this.loaded = false;
                this.loading = false;
                this.empty_search = true;
                this.keyword = "";
                this.$refs.search.focus();
              } else {
                this.search_result_all = res.data;
                this.search_result_feed = res.data.feedList.body;
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
      }, 700);
    },
    keywordClear() {
      this.keyword = "";
      this.autoTag = false;
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
      else {
        this.$router.push({name : "NotFound"});
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
    if (!this.$store.state.userStatus) {
      this.$router.push({name : "Home"});
    }
    this.checkquery();
  },
  watch: {
    keyword() {
      this.removeValue();
      if (this.keyword === "") {
        this.autotag = false;
      }
    },
  },
};
</script>

<style></style>
