html(lang:'en') {
	head {
		meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
		title(title)
		style{ yieldUnescaped """ 
			p {
				color: red;
				font-size: 46px;
			  }
			""" }
	}
	body {
		bodyContents()
	}
}
