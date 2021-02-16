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
              tabindex="0"              
            >
              <li 
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
  // autocompleteUser,
  autocompleteTag,
} from "@/api/search.js";
import GroupResult from "../components/Search/GroupResult.vue";
import Sidebar from "@/components/Common/Sidebar.vue";

// 방향키로 페이지 창 못 움직이게 하는 코드
window.addEventListener("keydown", function(e) {
    // space and arrow keys
    if([38, 40].indexOf(e.keyCode) > -1) {
        e.preventDefault();
    }
}, false);

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
      tags: [],
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
    // 인풋 창 검색어 넣고 포커스
    changeValue(str) {
      this.keyword = str;
      this.tags = [];
      this.removeValue();
      this.$refs.search.focus();
    },
    // 방향키로 태그리스트 이동 및 엔터로 검색창에 반영
    selectValue(keycode, str) {

      let hasClass = false;
      if (document.querySelector('.r').classList != null) {
        hasClass = document.querySelector('.r').classList.contains('key');
      }      

      const isTags = this.tags.length != 0;   
      if (keycode === 'down' && isTags) {
        if (!hasClass) {
          const thisEl = document.querySelectorAll('.r li')[0];
          document.querySelector('.r').classList.add('key');
          thisEl.classList.add('sel');
          thisEl.focus();
        } else {
          const lastEl = document.querySelector('.r li:last-child');
          const thisEl = document.querySelector('.r li.sel');
          const nextEl = thisEl.nextElementSibling;
          if (!lastEl.classList.contains('sel')) {
            thisEl.classList.remove('sel');
            nextEl.classList.add('sel');
            nextEl.focus();
          }
        }
      }
      if (keycode === 'up' && hasClass) {
        const firstEl = document.querySelectorAll('.r li')[0];
        const thisEl = document.querySelector('.r li.sel');
        const prevEl = thisEl.previousElementSibling;
        if (!firstEl.classList.contains('sel')) {
          thisEl.classList.remove('sel');
          prevEl.classList.add('sel');
          prevEl.focus();
        } else {
          this.$refs.search.focus();
        }
      }
      if (keycode === 'enter' && hasClass) {
        this.changeValue(str);
      }
      
    },
    // 검색창에서 마우스오버하거나 선택시 클래스리스트 비우기
    removeValue() {
      if (document.querySelector('.r').classList.contains('key')) {
        document.querySelector('.r').classList.remove('key');
        document.querySelector('.r li.sel').classList.remove('sel');
      }
    },

    select(e) {
      console.log("셀렉", e);
      this.model = e;
      console.log(this.model, "요기요!!");
    },
    autoTag(e) {
      console.log(e.target.value, "키워드 확인");
      this.keyword = e.target.value;
      autocompleteTag(
        this.keyword,
        (res) => {
          console.log(res.data);
          this.tags = res.data;
          console.log(this.tags);
        },
        (err) => {
          console.error(err);
        }
      );
    },
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
                this.keyword = '';
                this.$refs.search.focus();
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
  watch: {
    keyword() {
      this.removeValue();
    }
  }
};
</script>

<style src="vue-advanced-search/dist/AdvancedSearch.css"></style>
