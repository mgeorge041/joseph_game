package jogame.main;

public class GridLayout {
	
	private int rows;
	private int cols;

	private int row_pos[];
	private int col_pos[];

	public GridLayout(int rows, int cols, Scene scene) {
		this.rows = rows;
		this.cols = cols;
		row_pos = new int[rows + 1];
		col_pos = new int[cols + 1];
	}
	
	public void setRowPos(int row, int pos) {
		row_pos[row] = pos;
	}
	
	public void setRowsPos(int row[], int pos[]) {
		for(int i = 0; i < row.length; i++)
			row_pos[row[i]] = pos[i];
	}
	
	public void setColPos(int col, int pos) {
		col_pos[col] = pos;
	}
	
	public void setColsPos(int col[], int pos[]) {
		for(int i = 0; i < col.length; i++)
			col_pos[col[i]] = pos[i];
	}
	
	public int Row(int row) {
		return row_pos[row];
	}
	
	public int Col(int col) {
		return col_pos[col];
	}
	
	public int RowHeight(int row) {
		if(row < rows && row >= 0)
			return row_pos[row + 1] - row_pos[row];
		return 0;
	}
	
	public int ColWidth(int col) {
		if(col < cols && col >= 0)
			return col_pos[col + 1] - row_pos[col];
		return 0;
	}
}
