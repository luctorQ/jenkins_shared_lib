html(lang:'en') {
	head {
		meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
		title(title)
		style{  yieldUnescaped """ 
		table {
			margin: auto;
			//width: 600px;
			border-collapse: collapse;
			border: 1px solid #fff; /*for older IE*/
			border-style: hidden;
		}
			""" }
	}
	body { bodyContents() }
}
