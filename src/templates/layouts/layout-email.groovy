html(lang:'en') {
	head {
		meta('http-equiv':'"Content-Type" content="text/html; charset=utf-8"')
		title(title)
		style{  yieldUnescaped """ 
	body {
			font-family:monospace
		}
		table {
			margin: auto;
			width: 600px;
			border-collapse: collapse;
			border: 1px solid #fff; /*for older IE*/
			border-style: hidden;
		}
		table th.caption {
			background-color: #3a5aa0;
			color: #fff;
			font-weight: bold;
			letter-spacing: .3em;
		}
		table thead th {
			padding: 8px;
			background-color: #e9effb;
		}
		table th, table td {
			padding: 3px;
			border-width: 1px;
			border-style: solid;
			border-color: #3a5aa0;
			text-align: center;
		}
		table td.left-aligned {
			text-align:left
		}
			""" }
	}
	body { bodyContents() }
}
