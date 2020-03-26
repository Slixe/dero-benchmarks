package config;

group '/api', {	
	get '/benchmarks', 'main:benchmarks'

	group '', {
		get '/unconfirmedBenchmarks'
		post '/submit'
		post '/confirm'
		post '/delete'
	}, [
	action: 'main',
	middleware: 'auth'
	]

	group '/auth', {
		post '/validate'
		post '/login'
		post '/logout'
	}, [
	action: 'auth'
	]
}