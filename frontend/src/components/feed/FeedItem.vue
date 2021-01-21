<template>
  <div class="feed-item">
    <div class="top">
      <div class="profile-image" :style="{'background-image': 'url('+defaultProfile+')'}"></div>
      <div class="user-info">
        <div class="user-name">
          <button>SSAFY</button>
        </div>
        <p class="date">9시간 후</p>
      </div>
      <div class="content">
        <p>이 글은 아주 좋습니다.</p>
      </div>
    </div>
    <div class="feed-card">
      <div class="img" :style="{'background-image': 'url('+defaultImage+')'}"></div>
      <div class="contentsWrap">
        <h4 class="title">사용자경험(UX)을 이해하는 팀원이 되기 위하여 - 사용자에게 '기본적인' UX를 선사하기 위해 우리 모두 알아야할 사실들</h4>
        <div class="wrap">
          <div class="url">
            <a href="https://brunch.co.kr/@@63JW/25">https://brunch.co.kr/@@63JW/25</a>
          </div>
          <p class="date">2020.06.18</p>
        </div>
      </div>
    </div>
    <!---->
    <div class="btn-group wrap">
      <div class="like likeScrap">
        <svg
          class="svg-inline--fa fa-heart fa-w-16 icon full"
          aria-hidden="true"
          data-prefix="fas"
          data-icon="heart"
          role="img"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
          data-fa-i2svg
        >
          <path
            fill="currentColor"
            d="M462.3 62.6C407.5 15.9 326 24.3 275.7 76.2L256 96.5l-19.7-20.3C186.1 24.3 104.5 15.9 49.7 62.6c-62.8 53.6-66.1 149.8-9.9 207.9l193.5 199.8c12.5 12.9 32.8 12.9 45.3 0l193.5-199.8c56.3-58.1 53-154.3-9.8-207.9z"
          />
        </svg>
        <!-- <i class="fas fa-heart icon full"></i> -->
        <svg
          class="svg-inline--fa fa-heart fa-w-16 icon empty"
          aria-hidden="true"
          data-prefix="far"
          data-icon="heart"
          role="img"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
          data-fa-i2svg
        >
          <path
            fill="currentColor"
            d="M458.4 64.3C400.6 15.7 311.3 23 256 79.3 200.7 23 111.4 15.6 53.6 64.3-21.6 127.6-10.6 230.8 43 285.5l175.4 178.7c10 10.2 23.4 15.9 37.6 15.9 14.3 0 27.6-5.6 37.6-15.8L469 285.6c53.5-54.7 64.7-157.9-10.6-221.3zm-23.6 187.5L259.4 430.5c-2.4 2.4-4.4 2.4-6.8 0L77.2 251.8c-36.5-37.2-43.9-107.6 7.3-150.7 38.9-32.7 98.9-27.8 136.5 10.5l35 35.7 35-35.7c37.8-38.5 97.8-43.2 136.5-10.6 51.1 43.1 43.5 113.9 7.3 150.8z"
          />
        </svg>
        <!-- <i class="far fa-heart icon empty"></i> -->
        0
      </div>
      <div class="comment">
        <svg
          class="svg-inline--fa fa-comment-alt fa-w-16 icon"
          aria-hidden="true"
          data-prefix="far"
          data-icon="comment-alt"
          role="img"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
          data-fa-i2svg
        >
          <path
            fill="currentColor"
            d="M448 0H64C28.7 0 0 28.7 0 64v288c0 35.3 28.7 64 64 64h96v84c0 7.1 5.8 12 12 12 2.4 0 4.9-.7 7.1-2.4L304 416h144c35.3 0 64-28.7 64-64V64c0-35.3-28.7-64-64-64zm16 352c0 8.8-7.2 16-16 16H288l-12.8 9.6L208 428v-60H64c-8.8 0-16-7.2-16-16V64c0-8.8 7.2-16 16-16h384c8.8 0 16 7.2 16 16v288z"
          />
        </svg>
        <!-- <i class="far fa-comment-alt icon"></i> -->
        0
      </div>
      <!---->
      <div class="share">
        <svg
          class="svg-inline--fa fa-share-alt fa-w-14 icon"
          aria-hidden="true"
          data-prefix="fas"
          data-icon="share-alt"
          role="img"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 448 512"
          data-fa-i2svg
        >
          <path
            fill="currentColor"
            d="M352 320c-22.608 0-43.387 7.819-59.79 20.895l-102.486-64.054a96.551 96.551 0 0 0 0-41.683l102.486-64.054C308.613 184.181 329.392 192 352 192c53.019 0 96-42.981 96-96S405.019 0 352 0s-96 42.981-96 96c0 7.158.79 14.13 2.276 20.841L155.79 180.895C139.387 167.819 118.608 160 96 160c-53.019 0-96 42.981-96 96s42.981 96 96 96c22.608 0 43.387-7.819 59.79-20.895l102.486 64.054A96.301 96.301 0 0 0 256 416c0 53.019 42.981 96 96 96s96-42.981 96-96-42.981-96-96-96z"
          />
        </svg>
      </div>
    </div>
    <!---->
    <!---->
  </div>
</template>

<script>
import defaultImage from "../../assets/images/img-placeholder.png";
import defaultProfile from "../../assets/images/profile_default.png";
export default {
  data: () => {
    return { defaultImage, defaultProfile };
  }
};
</script>
