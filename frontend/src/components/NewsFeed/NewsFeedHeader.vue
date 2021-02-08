<template>
  <div style="padding-top:100px;width : 100%; height : 50vh; background-color : rgba(0,0,0,0.2)">
    <div class="newsfeed-header-title">
      {{ Category }} 헤더 일러스트 이미지가 들어갈 곳
    </div>

  </div>
</template>

<script>
export default {
  name: "NewsFeedHeader",
  props: {
    Category: Number,
  },
  data() {
    return {};
  },
  methods: {},
};
</script>
