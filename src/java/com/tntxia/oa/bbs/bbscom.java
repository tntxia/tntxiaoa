package com.tntxia.oa.bbs;

public class bbscom
{
  int i;
  int pagesCurrent = 0;
  int pagesNext = 0;
  int pagesLast = 0;

  public int getPagesCurrent()
  {
    return this.pagesCurrent;
  }

  public int getPagesLast()
  {
    return this.pagesLast;
  }

  public int getPagesNext()
  {
    return this.pagesNext;
  }

  public void setPages(int paramInt1, int paramInt2)
  {
    if (paramInt1 <= 0) {
      this.pagesCurrent = 0;
      this.pagesNext = 0;
      this.pagesLast = 0;
    }
    else {
      this.pagesCurrent = paramInt2;
      this.pagesNext = (paramInt2 + 1);
      this.pagesLast = (paramInt2 - 1);

      if (paramInt2 >= paramInt1) {
        this.pagesCurrent = paramInt1;
        this.pagesNext = 0;
        this.pagesLast = (paramInt2 - 1);
      }

      if (paramInt2 <= 1) {
        this.pagesCurrent = 1;
        this.pagesNext = (paramInt2 + 1);
        this.pagesLast = 0;
      }

      if (this.pagesNext > paramInt1) {
        this.pagesNext = paramInt1;
      }
      if (this.pagesNext == paramInt1) {
        this.pagesNext = 0;
      }

      if (this.pagesLast < 0) {
        this.pagesLast = 1;
      }
      if (this.pagesLast == 1)
        this.pagesLast = 0;
    }
  }
}