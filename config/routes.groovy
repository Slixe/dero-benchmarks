package config;

group '/api', {
	get '/benchmarks'
	get '/unconfirmedBenchmarks'
	post '/submit'
	post '/confirm'
	post '/delete'
}, [
	action: 'main'
]