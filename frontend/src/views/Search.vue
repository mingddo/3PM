<template>
  <section class="searchFrame">
    <div class="searchFilterList">
      <header class="seartchFilgerHeader">
        검색 결과
      </header>
      <div class="filters">
        <div class="searchFilter">
          <a
            @click="filter = 1"
            :class="[filter === 1 ? 'active' : '']"
            class="searchFilterTilte"
          >
            모두
          </a>
        </div>
        <div class="searchFilter">
          <a
            @click="filter = 2"
            :class="[filter === 2 ? 'active' : '']"
            class="searchFilterTilte"
          >
            게시물
          </a>
        </div>
        <div class="searchFilter">
          <a
            @click="filter = 3"
            :class="[filter === 3 ? 'active' : '']"
            class="searchFilterTilte"
          >
            사람
          </a>
        </div>
        <div class="searchFilter">
          <a
            @click="filter = 4"
            :class="[filter === 4 ? 'active' : '']"
            class="searchFilterTilte"
          >
            그룹
          </a>
        </div>
      </div>
    </div>
    <!-- mobile -->
    <div class="searchResultFrame">
      <!-- 컴포넌트 -->
      <div class="searchResultList">
        <GroupResults v-if="filter === 1" :category="category_group" />
        <GroupResults v-if="filter === 1" :category="category_person" />
        <span v-if="filter === 1 || filter === 2">
          <SerachResult v-for="(result, idx) in search_result" :key="idx" />
        </span>
        <span v-if="filter === 3">
          <FilterGroup />
        </span>
        <span v-if="filter === 4">
          <FilterGroup />
        </span>
      </div>
    </div>
    <!-- web 검색 결과 list -->
  </section>
</template>

<script>
import GroupResults from "@/components/Search/GroupResults.vue";
import SerachResult from "../components/Search/SerachResult.vue";
import FilterGroup from "../components/Search/FilterGroup.vue";
export default {
  components: { GroupResults, SerachResult, FilterGroup },
  data() {
    return {
      search_result: [{}, {}, {}, {}, {}, {}, {}, {}, {}, {}],
      category_group: "그룹",
      category_person: "사람",
      filter: 1,
    };
  },
  methods: {},
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}
.searchFrame {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  background-color: #e4e6e9;
}
.filters {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 2%;
}

.searchFilterList {
  width: 70%;
  height: auto;
  padding-top: 80px;
  /* position: sticky; */
  /* top: 0; */
  margin: auto;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: flex-start;
  background-color: #ffffff;
  box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.2);
}
.seartchFilgerHeader {
  width: 328px;
  height: 59px;
  margin: 16px;
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
  width: 20%;
  height: 55px;
  /* padding: 0 16px; */
  cursor: pointer;
}
.searchFilterTilte.active {
  background-color: #d8e7fa;
  font-weight: 900;
}

.searchFilterTilte {
  width: 100%;
  height: 100%;
  padding: 0 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  font-size: 16px;
  font-weight: 550;
}

.searchFilterTilte:hover {
  background-color: #d1d1d1;
}

.searchResultFrame {
  width: 750px;
  margin: 0 auto;
  padding-top: 80px;
}
.searchResultList {
  width: 100%;
  /* height: 700px; */
  padding: 32px;
  margin-bottom: 16px;
}
</style>
