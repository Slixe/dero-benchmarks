<template>
<div id="submit">
    <v-card class="bench elevation-5">
        <h2 class="bot">Submit new Benchmark</h2>
        <v-divider class="bot"></v-divider>
        <v-row align="center" justify="space-around">
            <v-form>
                <v-text-field autocomplete="vendor" label="Vendor" required></v-text-field>
                <v-text-field autocomplete="model" label="Model" required></v-text-field>
                <v-text-field autocomplete="hashrate" label="Hashrate" required></v-text-field>
                <v-text-field autocomplete="miner" label="Miner" required></v-text-field>
                <v-text-field autocomplete="user" label="User" class="text-field" equired></v-text-field>
                <v-btn color="blue">Sumbit</v-btn>
            </v-form>
        </v-row>
    </v-card>
</div>
</template>

<script>
export default {
    
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
.text-field {
    width: 100%;
    margin-bottom: 2%;
}
</style>
