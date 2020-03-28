<template>
  <div id="app">
    <v-app>
      <header class="header">
        <h1>DERO BENCHMARKS</h1>
      </header>
      <v-content>
          <transition name="fade">
            <router-view></router-view>
          </transition>
      </v-content>
    </v-app>
  </div>
</template>

<script>
export default {
  name: 'App',
  mounted() {
    let token = localStorage.getItem("token")

    if (token != null) {
      let headers = new Headers();
      headers.append("Authorization", "Bearer " + token)

      fetch(this.$api + "/api/auth/validate", { method: "POST", headers: headers }).then(result => result.json()).then(json => {
        if (json.logged) {
          localStorage.setItem("token", json.token)
        }
        else {
          localStorage.removeItem("token")
        }
      })
    }
  }
}
</script>

<style>
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
}

.header {
  margin-top: 2%;
}

* {
    transition: background-color 200ms ease, color 150ms ease-in-out;
}

.fade-enter-active,
.fade-leave-active {
  transition-property: opacity;
  transition-duration: 0.4s;
}

.fade-enter-active {
  transition-delay: 0.4s;
}

.fade-enter,
.fade-leave-active {
  opacity: 0;
}
</style>
