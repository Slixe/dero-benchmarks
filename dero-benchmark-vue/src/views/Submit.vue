<template>
<div id="submit">
    <v-card class="bench elevation-5">
        <h2 class="bot">Submit new Benchmark</h2>
        <v-alert v-show="alertShow" :type="alertType">{{ alertMessage }}</v-alert>
        <v-divider class="bot"></v-divider>
        <v-form class="submit-form" v-model="valid">
            <v-text-field v-model="vendor" autocomplete="vendor" label="Vendor" required></v-text-field>
            <v-text-field v-model="model" autocomplete="model" label="Model" required></v-text-field>
            <v-text-field v-model="memory" autocomplete="memory" label="Memory" required></v-text-field>
            <v-text-field v-model="hashrate" autocomplete="hashrate" label="Hashrate" required></v-text-field>
            <v-text-field v-model="miner" autocomplete="miner" label="Miner" required></v-text-field>
            <v-text-field v-model="user" autocomplete="user" label="User" class="text-field" equired></v-text-field>
            <v-btn @click="submit()" color="blue">Submit</v-btn>
        </v-form>
    </v-card>
    <h4>Back to <router-link to="/">Benchmarks</router-link></h4>
</div>
</template>

<script>
export default {
    data() {
        return {
            valid: false,
            vendor: "",
            model: "",
            memory: "",
            hashrate: "",
            miner: "",
            user: "",
            alertType: "error",
            alertMessage: "",
            alertShow: false,
            submitted: false,
        }
    },
    methods: {
        submit() {
            if (!this.submitted && this.valid) {
                this.submitted = true
                fetch(this.$api + "/api/submit", {
                    method: "POST",
                    body: JSON.stringify({
                        vendor: this.vendor.toUpperCase(),
                        model: this.model,
                        memory: this.memory,
                        hashrate: this.hashrate,
                        minerVersion: this.miner,
                        owner: this.user
                    })
                }).then(result => result.json()).then(json => {
                    this.alertType = json.success ? "success" : "error"
                    this.alertMessage = json.message
                    this.alertShow = true

                    if (json.success) {
                        setTimeout(() => this.$router.push("/"), 5000)
                    } else {
                        this.submitted = false
                    }
                }).catch(() => {
                    this.alertType = "error"
                    this.alertMessage = "An error has occurred !"
                    this.alertShow = true
                    this.submitted = false
                })
            }
        }
    }
}
</script>

<style scoped>
.bot {
    margin-bottom: 5%;
}
.bench {
    padding: 2%;
    margin: auto;
    margin-top: 5%;
    margin-bottom: 5%;
    width: 30%;
}
.submit-form {
    width: 70%;
    margin: auto;
}
</style>
