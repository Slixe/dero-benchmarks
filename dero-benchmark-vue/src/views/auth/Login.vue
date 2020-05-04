<template>
<div>
    <v-card class="center elevation-5">
        <v-alert v-show="alertShow" :type="alertType">{{ alertMessage }}</v-alert>
        <v-form v-model="valid">
            <v-text-field v-model="username" :counter="16" label="Username" required></v-text-field>
            <v-text-field v-model="password" type="password" :counter="64" label="Password" required></v-text-field>
            <v-btn @click="login()" color="blue">Login</v-btn>
        </v-form>
    </v-card>
</div>
</template>

<script>
export default {
    data() {
        return {
            valid: false,
            username: "",
            password: "",
            alertType: "error",
            alertMessage: "",
            alertShow: false
        }
    },
    mounted() {
        let token = localStorage.getItem("token")

        if (token != null)
        {
            this.$router.push("/unconfirmedBenchmarks")
        }
    },
    methods: {
        login() {
            if (this.valid) {
                fetch(this.$api + "/api/auth/login", {
                    method: "POST",
                    body: JSON.stringify({
                        username: this.username,
                        password: this.password
                    })
                }).then(result => result.json()).then(json => {
                    console.log(json)
                    let valid = json.token != null
                    this.alertType = valid ? "success" : "error"
                    this.alertMessage = valid ? "You are now logged in!" : json.message
                    this.alertShow = true
                    if (valid) {
                        localStorage.setItem("token", json.token)
                        setTimeout(() => this.$router.push("/unconfirmedBenchmarks"), 5000)
                    }
                }).catch(() => {
                    this.alertType = "error"
                    this.alertMessage = "An error has occurred !"
                    this.alertShow = true
                })
            }
        }
    }
}
</script>

<style scoped>
.center {
    padding: 2%;
    margin: auto;
    margin-top: 5%;
    margin-bottom: 5%;
    width: 30%;
}
</style>