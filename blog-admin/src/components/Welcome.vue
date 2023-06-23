<!--
 * @Date: 2023-06-23 10:21:51
 * @LastEditors: Fishing yoo725@hotmail.com
 * @LastEditTime: 2023-06-23 15:04:15
 * @FilePath: \BlogAdmin\src\components\Welcome.vue
 * @Description: https://github.com/iFishin
-->
<template>
  <div class="container">
    <h1 class="title">Welcome to Admin</h1>
    <p class="subtitle">🌳前人栽树，后人乘凉🐾</p>
    <!-- <button class="cta-button">Get Started</button> -->
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 80vh;
  background-color: #131313;
  color: #fff;
}

.title {
  font-size: 4rem;
  font-weight: bold;
  margin-bottom: 1rem;
  text-align: center;
  text-shadow: 2px 2px #000;
}

.subtitle {
  font-size: 1.5rem;
  margin-bottom: 3rem;
  text-align: center;
  text-shadow: 2px 2px #000;
}

.cta-button {
  padding: 1rem 2rem;
  border-radius: 25px;
  background-color: #fff;
  color: #000;
  text-decoration: none;
  text-transform: uppercase;
  font-weight: bold;
  letter-spacing: 2px;
  box-shadow: 2px 2px #000;
  transition: all 0.3s ease-in-out;
}

.cta-button:hover {
  transform: scale(1.1);
  box-shadow: 4px 4px #000;
}
</style>
